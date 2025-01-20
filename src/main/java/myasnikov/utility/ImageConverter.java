package myasnikov.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageConverter {

  public String convertImageToBase64(String imagePath) throws IOException {
    String base64Image = null;
    try (InputStream inputStream = getClass().getResourceAsStream(imagePath)) {
      if (inputStream != null) {
        byte[] imageBytes = inputStream.readAllBytes();
        base64Image = Base64.getEncoder().encodeToString(imageBytes);
      }
    } catch (IOException e) {
      /// ADD LOGS
      e.printStackTrace();
    }
    return base64Image;
  }
}
