package com.erfangc.sesamelab.shared

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.zaxxer.hikari.HikariDataSource
import org.apache.http.HttpHeaders
import org.apache.http.HttpHost
import org.apache.http.message.BasicHeader
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.sql.DataSource


@Configuration
open class SharedConfiguration {

    @Bean
    open fun queue(): Queue {
        return Queue("train-ner-model", true)
    }

    /*
    we are running on Heroku instead of directly on AWS EC2 or EBS
    we need to specify region specifically for S3 and DynamoDB instead of relying on instance profile
    */
    private val region = System.getenv("AWS_REGION")

    @Bean
    open fun restHighLevelClient(): RestHighLevelClient {
        val esAuthorization = System.getenv("ELASTICHSEARCH_AUTHORIZATION")
        val uri = URI(System.getenv("ELASTICSEARCH_URL"))
        return RestHighLevelClient(
                RestClient
                        .builder(HttpHost(uri.host, uri.port, uri.scheme))
                        .setDefaultHeaders(arrayOf(BasicHeader(HttpHeaders.AUTHORIZATION, esAuthorization)))
        )
    }

    @Bean
    open fun amazonS3(): AmazonS3 {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .build()
    }

    @Bean
    open fun connectionFactory(): ConnectionFactory {
        val rabbitMqUrl: URI
        try {
            rabbitMqUrl = URI(System.getenv("CLOUDAMQP_URL"))
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
        val factory = CachingConnectionFactory()
        factory.setUri(rabbitMqUrl)
        return factory
    }


    @Bean
    open fun dataSource(): DataSource {
        val dbUri = URI(System.getenv("DATABASE_URL"))
        val username = dbUri.userInfo.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        val password = dbUri.userInfo.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        val jdbcUrl = "jdbc:postgresql://" + dbUri.host + ':'.toString() + dbUri.port + dbUri.path + "?sslmode=require"
        val dataSource = HikariDataSource()
        dataSource.username = username
        dataSource.jdbcUrl = jdbcUrl
        dataSource.password = password
        dataSource.driverClassName = "org.postgresql.Driver"
        return dataSource
    }


}