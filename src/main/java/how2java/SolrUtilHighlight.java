
package how2java;
import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

public class SolrUtilHighlight {
    public static SolrClient client;
    private static String url;
    static {
        url = "http://localhost:8983/solr/how2java";
        client = new HttpSolrClient.Builder(url).build();
    }

    public static void queryHighlight(String keywords) throws SolrServerException, IOException {
        SolrQuery q = new SolrQuery();
        //��ʼҳ��
        q.setStart(0);
        //ÿҳ��ʾ����
        q.setRows(10);
        // ���ò�ѯ�ؼ���
        q.setQuery(keywords); 
        // ��������
        q.setHighlight(true); 
        // �����ֶ�
        q.addHighlightField("name"); 
        // �������ʵ�ǰ׺
        q.setHighlightSimplePre("<span style='color:red'>"); 
        // �������ʵĺ�׺
        q.setHighlightSimplePost("</span>"); 
        //ժҪ�100���ַ�
        q.setHighlightFragsize(100);
        //��ѯ
        QueryResponse query = client.query(q);

        //��ȡ�����ֶ�name��Ӧ���
        NamedList<Object> response = query.getResponse();
        NamedList<?> highlighting = (NamedList<?>) response.get("highlighting");
        for (int i = 0; i < highlighting.size(); i++) {
            System.out.println(highlighting.getName(i) + "��" + highlighting.getVal(i));
        }
        
        //��ȡ��ѯ���
        SolrDocumentList results = query.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.toString());
        }
    }

    public static <T> boolean batchSaveOrUpdate(List<T> entities) throws SolrServerException, IOException {

        DocumentObjectBinder binder = new DocumentObjectBinder();
		int total = entities.size();
		int count=0;
        for (T t : entities) {
            SolrInputDocument doc = binder.toSolrInputDocument(t);
            client.add(doc);
            System.out.printf("������ݵ������У��ܹ�Ҫ��� %d ����¼����ǰ��ӵ�%d�� %n",total,++count);
		}
        client.commit();
        return true;
    }

    public static QueryResponse query(String keywords,int startOfPage, int numberOfPage) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setStart(startOfPage);
        query.setRows(numberOfPage);
        
        query.setQuery(keywords);
        QueryResponse rsp = client.query(query);
        return rsp;
    }

}
