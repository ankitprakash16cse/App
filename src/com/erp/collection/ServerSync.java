package com.erp.collection;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerSync {

   /* final String SOAP_ACTION1 = "http://sample.com/Insetvideo";
    final String METHOD_NAME1 = "Insetvideo";
    final String NAMESPACE = "http://sample.com/";*/
    private Context context;
    Globals g;

    public ServerSync(Context context)
    {
        this.context = context;
        g = Globals.getInstance(this.context);
    }

    public String uploadImageServer(String sourceFileUri)
    {
        // for harsh sir
        String upLoadServerUri ="";// g.uploadURI;
        String fileName = sourceFileUri;//new File(sourceFileUri).getName();
        int serverResponseCode = 0;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 2 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile())
        {
            Log.e("uploadFile", "Source File Does not exist");
            return "0";
        }
        try
        {

            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP //the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("file", fileName);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage+ ": " + serverResponseCode);

            // close the streams //

            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (MalformedURLException ex)
        {
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        }
        catch (Exception e)
        {
            Log.e("Upload file to server1","Exception : " + e.getMessage());
        }
        return String.valueOf(serverResponseCode);
    }

    

    private String getMimeType(String path)
    {
        String extention= MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extention);
    }

    public HttpResponse get(String ctrl)throws IOException
    {
        String response="";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //Checking http request method type
            HttpGet httpGet=new HttpGet(ctrl);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            httpGet.setHeader("Accept-Encoding", "gzip");

            httpResponse = httpClient.execute(httpGet);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return httpResponse;
    }
    @SuppressWarnings("deprecation")
    public HttpResponse post( StringEntity stringEntity,String ctrl)throws IOException
    {
        String response="";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //Checking http request method type
            HttpPost httpPost = new HttpPost(ctrl);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip");
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return httpResponse;
    }
    
    public String uploadImageServer1(String sourceFileUri)
    {
        int responcecode=0;
        try
        {
            File f=new File(sourceFileUri);
            String contentType=getMimeType(f.getPath());
            String file_path=f.getAbsolutePath();
            OkHttpClient client=new OkHttpClient();
            RequestBody file_body=RequestBody.create(MediaType.parse(contentType),f);
            RequestBody request_body=new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("type",contentType)
                                    .addFormDataPart("upload_file",file_path.substring(file_path.lastIndexOf("/")+1),file_body)
                                    .build();
            Request request=new Request.Builder().url(Config.upLoadServerUri).post(request_body).build();
            try
            {
                Response response=client.newCall(request).execute();
                responcecode=response.code();
                if(!response.isSuccessful())
                {
                    throw new IOException("Error:" +response);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            Log.e("Upload file to server1","Exception : " + e.getMessage());
        }
        return String.valueOf(responcecode);
    }
}
