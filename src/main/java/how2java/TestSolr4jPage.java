package how2java;

import java.io.IOException;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class TestSolr4jPage {

	public static void main(String[] args) throws SolrServerException, IOException {
		//查询
		QueryResponse queryResponse = SolrUtil.query("name:手机",0,10);
		SolrDocumentList documents=	queryResponse.getResults();
		System.out.println("累计找到的条数："+documents.getNumFound());
		if(!documents.isEmpty()){
			
			Collection<String> fieldNames = documents.get(0).getFieldNames();
			for (String fieldName : fieldNames) {
				System.out.print(fieldName+"\t");
			}
			System.out.println();
		}
		
		for (SolrDocument solrDocument : documents) {
			
			Collection<String> fieldNames= solrDocument.getFieldNames();
			
			for (String fieldName : fieldNames) {
				System.out.print(solrDocument.get(fieldName)+"\t");
				
			}	
			System.out.println();
			
		}
	}

}
