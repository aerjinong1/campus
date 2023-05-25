package com.example.campus.tast;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.example.campus.entity.Address;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

public class ElasticsearchJava {

    private static ElasticsearchClient client = null;
    private static ElasticsearchAsyncClient asyncClient = null;

    private static synchronized void makeConnection() {
        // Create the low-level client
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("mycluster", "password"));

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost("192.168.56.1", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(
                            HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        RestClient restClient = builder.build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        client = new ElasticsearchClient(transport);
        asyncClient = new ElasticsearchAsyncClient(transport);
    }

    public static void main(String[] args) throws IOException {
        makeConnection();

        // Index data to an index products
        Address address = new Address(0L, 0L, "qieian");

        IndexRequest<Object> indexRequest = new IndexRequest.Builder<>()
                .index("address")
                .id("abc")
                .document(address)
                .build();

        client.index(indexRequest);

        Address address1 = new Address(0L, 0L, "qieian2");

        client.index(builder -> builder
                .index("address")
                .id(address1.getName() + "456")
                .document(address1)
        );

        // Search for a data
        TermQuery query = QueryBuilders.term()
                .field("name")
                .value("bag")
                .build();

        SearchRequest request = new SearchRequest.Builder()
                .index("address")
                .query(query._toQuery())
                .build();

        SearchResponse<Address> search =
                client.search(
                        request,
                        Address.class
                );

        for (Hit<Address> hit : search.hits().hits()) {
            Address ad = hit.source();
            System.out.println(ad);
        }

        SearchResponse<Address> search1 = client.search(s -> s
                        .index("address")
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value(v -> v.stringValue("bag"))
                                )),
                Address.class);

        for (Hit<Address> hit : search1.hits().hits()) {
            Address ad = hit.source();
            System.out.println(ad);
        }

        // Splitting complex DSL
        TermQuery termQuery = TermQuery.of(t -> t.field("name").value("bag"));

        SearchResponse<Address> search2 = client.search(s -> s
                        .index("address")
                        .query(termQuery._toQuery()),
                Address.class
        );

        for (Hit<Address> hit : search2.hits().hits()) {
            Address ad = hit.source();
            System.out.println(ad);
        }

        // Creating aggregations
        SearchResponse<Void> search3 = client.search(b -> b
                        .index("address")
                        .size(0)
                        .aggregations("price-histo", a -> a
                                .histogram(h -> h
                                        .field("xvalue")
                                        .interval(2.0)
                                )
                        ),
                Void.class
        );

        Query query1 = Query.of(
                q -> q.match(
                        m -> m.field("qi")
                                .query(
                                        FieldValue.of("qi")
                                )
                )
        );
        System.out.println(query1.toString()+"String");

        long firstBucketCount = search3.aggregations()
                .get("price-histo")
                .histogram()
                .buckets().array()
                .get(0)
                .docCount();

        System.out.println("doc count: " + firstBucketCount);
    }

}