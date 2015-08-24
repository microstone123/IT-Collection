package assembly.stone.itassembly.jsouphttp;


public interface HttpCallback {

 public void onRequestSuccess(int id, BaseResponse result);

 public void onRequestFail(int id, String reason);

 public void onRequestCancel(int id);

 public static class SimpleCallback implements HttpCallback {

  @Override
  public void onRequestSuccess(int id, BaseResponse result) {
  }

  @Override
  public void onRequestFail(int id, String reason) {
  }

  @Override
  public void onRequestCancel(int id) {
  }

 }

}
