package com.erfangc.sesamelab.shared

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.RangeQueryBuilder
import org.elasticsearch.index.query.TermQueryBuilder
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Service
import java.time.Instant

data class SearchCorpusResponse(val documents: List<Document>, val totalPages: Long)

@Service
class ElasticsearchDocumentService(private val highLevelClient: RestHighLevelClient,
                                   private val objectMapper: ObjectMapper) {
    val pageSize = 10
    private val index = "ner_model_documents"

    fun searchByCorpusID(corpusID: Long,
                         modifiedAfter: Instant?,
                         page: Int? = null): SearchCorpusResponse {
        val boolQueryBuilder = BoolQueryBuilder().filter(TermQueryBuilder("corpusID", corpusID))
        modifiedAfter?.run { boolQueryBuilder.filter(RangeQueryBuilder("lastModifiedOn").gte(this)) }
        val searchRequest = SearchRequest(
                arrayOf(index),
                SearchSourceBuilder()
                        .from(((page ?: 1) - 1) * pageSize)
                        .size(pageSize)
                        .query(
                                boolQueryBuilder
                        )
        )
        val searchResponse = highLevelClient.search(searchRequest)
        val docs = searchResponse.hits.map { objectMapper.readValue<Document>(it.sourceAsString) }
        return SearchCorpusResponse(docs, totalPages = (searchResponse.hits.totalHits / pageSize) + 1)
    }

    fun searchByCreator(creatorID: String): List<Document> {
        val searchRequest = SearchRequest(
                arrayOf(index),
                SearchSourceBuilder()
                        .query(
                                BoolQueryBuilder()
                                        .filter(TermQueryBuilder("creatorID", creatorID))
                        )
        )
        val searchResponse = highLevelClient.search(searchRequest)
        return searchResponse.hits.map { objectMapper.readValue<Document>(it.sourceAsString) }
    }

}