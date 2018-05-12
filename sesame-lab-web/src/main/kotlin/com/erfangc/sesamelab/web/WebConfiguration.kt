package com.erfangc.sesamelab.web

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.auth0.client.auth.AuthAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfiguration {
    /*
    we are running on Heroku instead of directly on AWS EC2 or EBS
    we need to specify region specifically for S3 and DynamoDB instead of relying on instance profile
    */
    private val region = System.getenv("AWS_REGION")
    /*
    needed to validate JWT tokens issued by our trusted authorization provider
     */
    private val issuer = System.getenv("AUTH0_ISSUER")
    /*
    client ID representing this server as an OAuth 2 client (this is NOT the same Client ID as the UI)
     */
    private val clientId = System.getenv("AUTH0_CLIENT_ID")
    private val clientSecret = System.getenv("AUTH0_CLIENT_SECRET")

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(region)
                .build()
    }

    @Bean
    fun dynamoDB(amazonDynamoDB: AmazonDynamoDB): DynamoDB {
        return DynamoDB(amazonDynamoDB)
    }

    @Bean
    fun authAPI(): AuthAPI {
        return AuthAPI(issuer, clientId, clientSecret)
    }


}