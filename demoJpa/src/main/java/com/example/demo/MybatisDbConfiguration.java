package com.example.demo;

import javax.jdo.annotations.PrimaryKey;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
/*EnableJpaRepositoryis와 bacsePackges와 MapperScan의 bascePackages가 같으면 컴파일에러남.*/
@EnableJpaRepositories(basePackages =  "com.example.demo.db.repository",
 	entityManagerFactoryRef = "entityManagerFactory",
  	transactionManagerRef = "transactionManager")

@MapperScan( basePackages =  "com.example.demo.db.da" , 
	sqlSessionFactoryRef ="sqlSessionFactory" ,
	sqlSessionTemplateRef="sqlSessionTemplate"
)
@PropertySource("classpath:/application.yaml")
public class MybatisDbConfiguration {
	@PrimaryKey
	@Bean(name="dataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource(){
		return DataSourceBuilder.create().build();
	}

	@PrimaryKey
	@Bean(name="jpaProperties")
	@ConfigurationProperties(prefix = "spring.jpa")
	public JpaProperties jpaProperties(){
		return new JpaProperties();
	}

	@PrimaryKey
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
		EntityManagerFactoryBuilder builder,
		@Qualifier("dataSource") DataSource primaryDataSource,
		@Qualifier("jpaProperties") JpaProperties jpaProperties
	){
		return builder.dataSource(primaryDataSource)
		.properties(jpaProperties.getProperties())
		.packages("com.example.demo.db")
		.persistenceUnit("default")
		.build();
	}

	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.example.demo.db");
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name="sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(
		@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}