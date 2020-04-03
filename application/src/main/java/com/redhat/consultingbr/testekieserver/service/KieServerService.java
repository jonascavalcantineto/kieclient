package com.redhat.consultingbr.testekieserver.service;

import java.io.IOException;

import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.common.rest.KieServerHttpRequest;
import org.springframework.stereotype.Service;

import br.gov.ce.fortaleza.sepog.domain.IssueChamadoKSPessoa;
@Service
public class KieServerService {

	private KieServicesConfiguration conf;
	
	 private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
	
	public RuleServicesClient criarConexaoKieServer(String url,String  username,String password) throws Exception {
		conf = KieServicesFactory.newRestConfiguration(url, username, password);
		
        Set<Class<?>> allClasses = new HashSet<Class<?>>();
        allClasses.add(IssueChamadoKSPessoa.class);
	/*	allClasses.add(tabelaInstrutoria.class);
		allClasses.add(AtrasoRestituicao.class);
		allClasses.add(BaseCargoComissionado.class);
		allClasses.add(BaseCargoComissionado.class);
		allClasses.add(BaseInstrutoriaCCO.class);
		allClasses.add(BaseIRPF.class);
		allClasses.add(FaltaRestituicao.class);
		allClasses.add(Funcionario.class);
		allClasses.add(Pensao.class);
		allClasses.add(ReferencialAdicionalNoturno.class);
		allClasses.add(TabelaCargoComissionado.class);
		allClasses.add(TabelaIncidencia.class);
		allClasses.add(tabelaInstrutoria.class);
		allClasses.add(TabelaIRPF.class);
		allClasses.add(TabelaParametros.class);
		allClasses.add(TabelaPlantaoExtra.class);
		allClasses.add(TabelaVerba.class);
		allClasses.add(TetoSalarial.class);
		allClasses.add(TipoVerbaOficial.class);
		allClasses.add(Verba.class); */
		conf.addExtraClasses(allClasses);
		
		if (url.toLowerCase().startsWith("https")) {
			conf.setUseSsl(true);
            forgiveUnknownCert();
        }
		System.out.println(conf.toString());
		
		conf.setMarshallingFormat(FORMAT); 
		
		KieServicesClient client = KieServicesFactory.newKieServicesClient(conf);
		RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

		return ruleClient;
	}

	  // only needed for non-production test scenarios where the TLS certificate isn't set up properly
    private void forgiveUnknownCert() throws Exception {
        KieServerHttpRequest.ConnectionFactory connf = new KieServerHttpRequest.ConnectionFactory() {

            public HttpURLConnection create(URL u) throws IOException {
                return forgiveUnknownCert((HttpURLConnection) u.openConnection());
            }

            public HttpURLConnection create(URL u, Proxy p) throws IOException {
                return forgiveUnknownCert((HttpURLConnection) u.openConnection(p));
            }

            private HttpURLConnection forgiveUnknownCert(HttpURLConnection conn) throws IOException {
                if (conn instanceof HttpsURLConnection) {
                    HttpsURLConnection sconn = HttpsURLConnection.class.cast(conn);
                    sconn.setHostnameVerifier(new HostnameVerifier() {

                        public boolean verify(String arg0, SSLSession arg1) {
                            return true;
                        }
                    });
                    try {
                        SSLContext context = SSLContext.getInstance("TLS");
                        context.init(null, new TrustManager[]{
                                new X509TrustManager() {

                                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                    }

                                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                    }

                                    public X509Certificate[] getAcceptedIssuers() {
                                        return null;
                                    }
                                }
                        }, null);
                        sconn.setSSLSocketFactory(context.getSocketFactory());
                    } catch (Exception e) {
                        throw new IOException(e);
                    }
                }
                return conn;
            }
        };
        Field field = KieServerHttpRequest.class.getDeclaredField("CONNECTION_FACTORY");
        field.setAccessible(true);
        field.set(null, connf);
    }
}
