package com.example.demo;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan("com.example.demo.db.da")
@PropertySource("classpath:/application.yaml")
public class MybatisDbConfiguration {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	/*
	 * @Primary
	 * 
	 * @Bean(destroyMethod="close") public DataSource dataSource() throws Exception
	 * { DataSource dataSource = new HikariDataSource(hikariConfig()); return
	 * dataSource; }
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		//hikariDataSource.setDriverClassName("org.postgresql.Driver");
		hikariDataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariDataSource.setUsername("postgres");
		hikariDataSource.setPassword("pwd");
		//hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/stockweb");
		hikariDataSource.setJdbcUrl("jdbc:log4jdbc:postgresql://localhost:5432/stockweb");
		
		return hikariDataSource;

	}

	/*
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceUnitName("hello");
		entityManagerFactoryBean.setPersistenceXmlLocation("classpath:/META-INF/persistence.xml");
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();
	}
	*/
/*
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
*/	

}