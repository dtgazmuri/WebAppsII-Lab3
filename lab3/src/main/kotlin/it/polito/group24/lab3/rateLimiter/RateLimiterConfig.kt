package it.polito.group24.lab3.rateLimiter

import RateLimitInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class RateLimiterConfig : WebMvcConfigurer {

    private val interceptor: RateLimitInterceptor? = null

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(interceptor!!)
            .addPathPatterns("/user/**")
    }
}