package com.learn.erp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public Caffeine<Object, Object> caffeineConfig(){
		return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .recordStats()
                .maximumSize(10_000);				
	}
	
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(    
        		"users",
                "salaries",
                "bonuses",
                "payrolls",
                "attendanceRecords",
                "sales",
                "saleItems",
                "customers",
                "products",
                "suppliers"
            );
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}