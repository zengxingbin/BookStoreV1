package com.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class FileUploadUtils {
    /**
     * 
     * @param request
     * @param paramMap
     * @param storeDirectoryPath
     * @throws FileUploadException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void fileUpload(HttpServletRequest request,
            Map<String, Object[]> paramMap, String storeDirectoryPath) throws FileUploadException,
            UnsupportedEncodingException, Exception {
        // ����������DiskFileItemFactory����
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();            
        // ʹ�ù������󴴽���������
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        // ʹ�ý���������ServletRequest����
        List<FileItem> fileItems = fileUpload.parseRequest(request);
        Iterator<FileItem> iterator = fileItems.iterator();
        while(iterator.hasNext()) {
            FileItem fileItem = iterator.next();
            if(fileItem.isFormField()) {
                processFromFiled(fileItem, paramMap);
            } else {

                fileUpload.setHeaderEncoding("UTF-8");
                
                processUploadedFiled(fileItem, paramMap, storeDirectoryPath);
            }
        }
    }

    /**
     * 
     * @param fileItem
     * @param paramMap
     * @param storeDirectoryPath2 
     * @throws Exception
     */
    private static void processUploadedFiled(FileItem fileItem,
            Map<String, Object[]> paramMap, String storeDirectoryPath) throws Exception {
        String fileName = fileItem.getName();
        fileName = FilenameUtils.getName(fileName);
//      fileName = fileName.trim();
        if(fileName != null && !"".equals(fileName)) {
            File storeDirectory = new File(storeDirectoryPath);
            if(!storeDirectory.exists()) {
                storeDirectory.mkdirs();
            }
            
            // Ŀ¼��ɢ
            String childDirectoryName = makeChildDirectoryByHashCode(storeDirectory, fileName);
            File file = new File(storeDirectory, childDirectoryName + File.separator + fileName);
            fileItem.write(file);
            fileItem.delete();
            
            paramMap.put("bookCoverPath", new String[]{File.separator + childDirectoryName + File.separator + fileName});
        }       
    }

    /**
     * 
     * @param fileItem
     * @param paramMap
     * @throws UnsupportedEncodingException
     */
    private static void processFromFiled(FileItem fileItem,
            Map<String, Object[]> paramMap) throws UnsupportedEncodingException {
        String name = fileItem.getFieldName();
        String value = fileItem.getString("UTF-8");
        
        if(paramMap.containsKey(name)) {
            Object[] values = paramMap.get(name);
            List<Object> valueList = new ArrayList<Object>(Arrays.asList(values));
            valueList.add(value);
            
            paramMap.put(name, valueList.toArray());
        } else {
            paramMap.put(name, new String[]{value});
        }
    }
    
    /**
     * �ļ���ɢ-�����ļ�����ϣֵ 
     * @param storeDirectory
     * @param fileName
     * @return
     */
    private static String makeChildDirectoryByHashCode(File storeDirectory, String fileName) {
        // ��ȡ�ļ���HashCode
        int fileNameHashCode = fileName.hashCode();
        // ��HashCodeת��Ϊ16����
        String fileNameHashCodeHex = Integer.toHexString(fileNameHashCode);
        String childDirectoryName = fileNameHashCodeHex.charAt(0) + (fileNameHashCodeHex.length() > 1 ? File.separator +  fileNameHashCodeHex.charAt(1) : "");
        File childDirectory = new File(storeDirectory, childDirectoryName);
        if(!childDirectory.exists())
            childDirectory.mkdirs();
        return childDirectoryName;
    }
    
    /**
     * �ļ���ɢ-�������ڸ�ʽ
     * @param storeDirectory
     * @return
     */
    @SuppressWarnings("unused")
    private static String makeChildDirectoryByData(File storeDirectory) {
        String childDirectoryName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File childDirectory = new File(storeDirectory, childDirectoryName);
        if(!childDirectory.exists())
            childDirectory.mkdirs();
        return childDirectoryName;
    }
}
