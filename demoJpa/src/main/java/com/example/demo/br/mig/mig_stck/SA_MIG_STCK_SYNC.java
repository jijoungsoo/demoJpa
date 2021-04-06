package com.example.demo.br.mig.mig_stck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_GEN;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MENU;
import com.example.demo.db.da.stck.DA_STCK_MARCAP;
import com.example.demo.db.domain.marcap.StckMarcap;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;

@OpService
@Service
public class SA_MIG_STCK_SYNC {
    @Autowired
    DA_STCK_MARCAP daStckMarcap;

    @Autowired
    PjtUtil pjtU;
    
    
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-SA_MIG_STCK_SYNC")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
	}

	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-SA_MIG_STCK_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-SA_MIG_STCK_SYNC", description = "출력데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	@Autowired
	DA_MIG_AV_GEN daMigAvGen;

	@Autowired
	DA_MIG_AV_MENU daMigAvMenu;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"MARCAP"}, value = "주식")
    public void run(IN_DS inDs) throws BizException {
        try {
            execute("git pull");
        } catch( IOException e){

            e.printStackTrace();

        } catch( InterruptedException e){

            e.printStackTrace();
        }
        
        System.out.println("ccccc");
        Calendar cal = Calendar.getInstance();
        System.out.println(cal);

        String YYYYMMDD =daStckMarcap.findYYYYMMDDById();
        System.out.println(YYYYMMDD);
        String YYYY = YYYYMMDD.substring(0,4);
        System.out.println(YYYY);
       
        
    
        int year = cal.get(Calendar.YEAR);
        System.out.println(year);
        ArrayList<File> al= getFileDir();
        ArrayList<StckMarcap> al_stock = new ArrayList<StckMarcap>();
        int cnt=0;
        for(int i=0;i<al.size();i++){
            File f = al.get(i);

            
            String before_tmp = "marcap-"+YYYY+".csv.gz";
            String after_tmp = "marcap-"+year+".csv.gz";
            if(f.getName().compareTo(before_tmp)>=0  || f.getName().compareTo(after_tmp)>=0 ){//올해 데이터만 다시 올린다.
                try {
                    ArrayList<String> al_tmp  = decompress(f);
                    for(int j=0;j<al_tmp.size();j++){
                        String t = al_tmp.get(j);
                        if(j==0){
                            continue;//건너 띄기
                        }

                        String[] arr_tmp = t.split(",");
                        String STOCK_CD = arr_tmp[0].replaceAll("\"", "").trim();
                        String STOCK_DT = arr_tmp[17].replaceAll("-", "").trim();
                        STOCK_DT = STOCK_DT.replaceAll("\"", "").trim();
                        String STOCK_NM = arr_tmp[1].replaceAll("\"", "").trim();
                        
                        if(STOCK_DT.compareTo(YYYYMMDD)>0){
                            //System.out.println(STOCK_DT);
                            //System.out.println(YYYYMMDD);
                            //System.out.println(STOCK_DT.compareTo(YYYYMMDD));
                            //"Code","Name","Market","Dept","Close","ChangeCode","Changes","ChagesRatio","Open","High","Low","Volume","Amount","Marcap","Stocks","MarketId","Rank","Date"
                            //https://github.com/FinanceData/marcap
                            /*
                            "005930"            [0]Code[종목코드]///STOCK_CD
                            ,"삼성전자"         [1]Name[종목이름]///STOCK_NM
                            ,"KOSPI"            [2]Market[시장]///
                            ,""                 [3]Dept[부서(한국거래소)]///                            
                            ,83000.0            [4]Close[종가]///CLS_AMT       
                            ,"1"                [5]ChangeCode///CHANGES_AMT        
                            ,2000.0             [6]Changes[전일대비]///CHANGES_AMT                      
                            ,2.47               [7]ChagesRatio[전일대비 등락률]///CHANGES_RT    
                            ,81000.0            [8]Open[시가]///START_AMT
                            ,84400.0            [9]High[고가]///HIGH_AMT
                            ,80200.0            [10]Low[저가]///LOW_AMT
                            ,38655276.0         [11]Volume[거래량]///TRADE_QTY
                            ,3185356823460.0    [12]Amount[거래대금]///TRADE_AMT
                            ,495491951650000.0  [13]Marcap[시가총액(백만원)]///TOTAL_MRKT_AMT
                            ,5969782550         [14]Stocks[상장주식수]///STOCK_CNT
                            ,"STK"              [15]MarketId[시장기호]//--------------
                            ,1                  [16]Rank[시가총액 순위 (당일)]///RNK
                            ,"2021-01-04"       [17]Date[날짜]///STOCK_DT
                            */
                            Integer CLS_AMT = 0;
                            if(pjtU.isEmpty(arr_tmp[4].trim().replaceAll("\"", ""))){

                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[4].trim().replaceAll("\"", ""));
                                CLS_AMT  = tmp_int.intValue(); //종가
                            }

                            Integer CHANGES_AMT=0;  //전일대비
                            if(pjtU.isEmpty(arr_tmp[6].trim())){

                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[6].trim());
                                CHANGES_AMT  = tmp_int.intValue(); //종가
                            }
                            Double CHANGES_RT=Double.parseDouble(arr_tmp[7].trim());  //전일비
                            Long TRADE_QTY=0L;  //거래량
                            if(pjtU.isEmpty(arr_tmp[11].trim())){
                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[11].trim());
                                TRADE_QTY  = tmp_int.longValue(); //종가
                            }
                            Long TRADE_AMT=0L;  //거래대금
                            if(pjtU.isEmpty(arr_tmp[12].trim())){
                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[12].trim());
                                TRADE_AMT  = tmp_int.longValue(); //종가
                            }
                            Integer START_AMT=0;  //시가
                            if(pjtU.isEmpty(arr_tmp[8].trim())){
                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[8].trim());
                                START_AMT  = tmp_int.intValue(); //종가
                            }
                            Integer HIGH_AMT=0;   //고가
                            if(pjtU.isEmpty(arr_tmp[9].trim())){
                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[9].trim());
                                HIGH_AMT  = tmp_int.intValue(); //종가
                            }
                            Integer LOW_AMT=0;  //저가
                            if(pjtU.isEmpty(arr_tmp[10].trim())){
                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[10].trim());
                                LOW_AMT  = tmp_int.intValue(); //종가
                            }
                            Long TOTAL_MRKT_AMT=0L;  //시가총액(백만원)
                            if(pjtU.isEmpty(arr_tmp[13].trim())){
                            } else {
                                Double tmp_int = Double.parseDouble(arr_tmp[13].trim());
                                TOTAL_MRKT_AMT  = tmp_int.longValue(); //종가
                            }
                            Double TOTAL_MRKT_AMT_RT = 0D;  //시가총액비중(%)
                            Double STOCK_CNT =0D;//상장주식수
                            try {
                                STOCK_CNT=Double.parseDouble(arr_tmp[14].trim());
                            } catch (Exception e){
                            }
                            Double FRGN_CNT = 0D;  //외국인 보유주식수
                            Double FRGN_RT = 0D;  //외국인 지분율(%)                                               
                            Integer RNK =  Integer.parseInt(arr_tmp[16]);//시가총액 순위

                            Optional<StckMarcap> m = daStckMarcap.findById(STOCK_CD, STOCK_DT);
                            if(m.isPresent()){

                            } else {
                                cnt++;
                                if(cnt%10000==0){
                                    System.out.println("in cnt=>"+cnt);
                                }                                
                                StckMarcap m2 =StckMarcap.builder()
                                .stockCd(STOCK_CD)
                                .stockDt(STOCK_DT)
                                .stockNm(STOCK_NM)
                                .clsAmt(CLS_AMT)
                                .changesAmt(CHANGES_AMT)
                                .changesRt(CHANGES_RT)
                                .tradeQty(TRADE_QTY)
                                .tradeAmt(TRADE_AMT)
                                .startAmt(START_AMT)
                                .highAmt(HIGH_AMT)
                                .lowAmt(LOW_AMT)
                                .totalMrktAmt(TOTAL_MRKT_AMT)
                                .totalMrktAmtRt(TOTAL_MRKT_AMT_RT)
                                .stockCnt(STOCK_CNT)
                                .rnk(RNK)
                                .updtDtm(new Date())
                                .crtDtm(new Date())
                                .build();
                                al_stock.add(m2);
                            }
                        }
                    }
                    //System.out.println(tmp2);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        }

        daStckMarcap.crtStckMrcp(al_stock);
    }

    public ArrayList<File> getFileDir(){
        String isDir = "D:\\marcap\\data";         
        // 하위 디렉토리 
        for (File info : new File(isDir).listFiles()) {
            if (info.isDirectory()) {
                System.out.println(info.getName());
            }
            if (info.isFile()) {
                System.out.println(info.getName());
            }
        }
        ArrayList<File>  al = new ArrayList<File>();
         for (File info : FileUtils.listFiles(new File(isDir), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            System.out.println(info.getName());
            System.out.println(info.getAbsolutePath());
            al.add(info);
        }
        return al;

    }

    public ArrayList<String> decompress(File f) throws IOException {
        ArrayList<String> al = new ArrayList<String>();
          //final StringBuilder outStr = new StringBuilder();
          final GZIPInputStream gis = new GZIPInputStream(new FileInputStream(f));
          final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
          String line;
          while ((line = bufferedReader.readLine()) != null) {
            al.add(line);
            //outStr.append(line);
          }
          return al;
          //return outStr.toString();
      }

    public void execute(String cmd) throws IOException ,InterruptedException {
        System.out.println("bbb");
        //https://cofs.tistory.com/365
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        StringBuffer successOutput = new StringBuffer(); // 성공 스트링 버퍼
        StringBuffer errorOutput = new StringBuffer(); // 오류 스트링 버퍼
        BufferedReader successBufferReader = null; // 성공 버퍼
        BufferedReader errorBufferReader = null; // 오류 버퍼
        String msg = null; // 메시지
 
        List<String> cmdList = new ArrayList<String>();
 
        // 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
        if (System.getProperty("os.name").indexOf("Windows") > -1) {
            cmdList.add("cmd");
            cmdList.add("/y");
            cmdList.add("/c");
        } else {
            cmdList.add("/bin/sh");
            cmdList.add("-c");
        }
        // 명령어 셋팅
        cmdList.add(cmd);
        String[] array = cmdList.toArray(new String[cmdList.size()]);

        // 명령어 실행
        process = runtime.exec(array,null,new File("D:\\marcap"));

        // shell 실행이 정상 동작했을 경우
        successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));

        while ((msg = successBufferReader.readLine()) != null) {
            successOutput.append(msg + System.getProperty("line.separator"));
        }
        successBufferReader.close();

        // shell 실행시 에러가 발생했을 경우
        errorBufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
        while ((msg = errorBufferReader.readLine()) != null) {
            errorOutput.append(msg + System.getProperty("line.separator"));
        }
        errorBufferReader.close();

        // 프로세스의 수행이 끝날때까지 대기

        process.getErrorStream().close(); 
        process.getInputStream().close(); 
        process.getOutputStream().close(); 

        //process.waitFor();    이거쓰면 먹통됨  주석처리

        // shell 실행이 정상 종료되었을 경우
        if (process.exitValue() == 0) {
            System.out.println("성공");
            System.out.println(successOutput.toString());
        }

        
        process.destroy();
        return ;

        
        
    }
    
}