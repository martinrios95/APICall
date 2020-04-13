package network;

public class APIResponse implements HTTPResponse {
    protected int code;
    protected String body;
    protected String status;

    public APIResponse(){
        this(HTTPCodes.OK, HTTPStatus.OK, "");
    }

    public APIResponse(String response){
        this(HTTPCodes.OK, HTTPStatus.OK, response);
    }

    public APIResponse(APIResponse network){
        this(network.getCode(), network.getStatus(), network.getBody());
    }

    public APIResponse(int code, String status, String body){
        setCode(code);
        setStatus(status);
        setBody(body);
    }

    public void setCode(int code){
        this.code = code;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setBody(String body){
        this.body = body;
    }

    @Override
    public String toString(){
        return this.code + " " + this.status;
    }

    @Override
    public int getCode(){
        return code;
    }

    @Override
    public String getStatus(){
        return status;
    }

    @Override
    public String getBody(){
        return body;
    }

    @Override
    public APIResponse clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("HTTP responses are not allowed to clone!");
    }
}
