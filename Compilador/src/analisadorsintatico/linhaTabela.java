package analisadorsintatico;

/**
 * 
 * @author Eduardo Gonçalves da Silva
 */
public class linhaTabela {
    private String token;
    private String local;

    public linhaTabela(String token, String local) {
        this.token = token;
        this.local = local;
    }

    public linhaTabela() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
