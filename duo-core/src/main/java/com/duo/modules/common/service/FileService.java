package com.duo.modules.common.service;

import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.SpringContextHolder;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.system.entity.SystemAttachment;
import com.duo.modules.system.mapper.SystemAttachmentMapper;
import com.duo.modules.tool.entity.ToolFunction;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author [ Yonsin ] on [2020/1/22]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
public class FileService extends BaseService {
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private SystemAttachmentMapper systemAttachmentMapper;//附件表

    public Result<?> upload(Map<String, MultipartFile> fileMap,Map<String, Object> requestMap,String ip){
        List<Map> file_url_list = Lists.newArrayList();
        String funId= (String) requestMap.get("funId");
        String keyId= (String) requestMap.get("keyId");
        String typeId= (String) requestMap.get("typeId");
        String auditing= (String) requestMap.get("auditing");

        DynamicDataSource.setDataSource("default");
        ToolFunction funinfo = layoutService.getFunctionInfo(funId);
        DynamicDataSource.clearDataSource();
        String tableName = funinfo != null ? funinfo.getTable_name() : "";

        for (MultipartFile file:fileMap.values()) {
            String MD5=getMd5(file);
            String size= FileUtils.getPrintSize(file.getSize());
            log.info("上传文件===" + "\n");
            //判断文件是否为空
            if (file.isEmpty()) {
                return Result.failure("500", "上传文件不可为空");
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            String fileType = "";
            if (fileName.indexOf(".") > 0) fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            log.info("上传的文件名为: " + fileName + "\n");
            fileName = fileName.substring(0, fileName.lastIndexOf(".") - 1);
            String fileID = getKeyUID();
            String newfileName = funId + fileID;
            log.info("（加个时间戳，尽量避免文件名称重复）保存的文件名为: " + newfileName + "\n");
            String type = MemCache.getSystemParameter("fileServerType");;
            if (StringUtils.isEmpty(type)) type = "file";
            String path = newfileName;

            //查重，避免重复上传
            SystemAttachment query=new SystemAttachment();
            query.setFile_md5(MD5);
            DynamicDataSource.setDataSource("default");
            List<SystemAttachment> ls=systemAttachmentMapper.select(query);
            if(ls!=null&&ls.size()>0){
                SystemAttachment attachment = ls.get(0);
                log.info("查询到已有相同文件存在，不再保存新文件！"+attachment.getFile_path());
                attachment.setAuditing(auditing);
                attachment.setFile_id(fileID);
                attachment.setData_id(keyId);
                attachment.setFile_date(new Date());
                attachment.setFile_name(fileName);
               // attachment.setFile_path(path);  //用已有文件的path
                attachment.setFile_size(size);
                attachment.setFile_md5(MD5);
                attachment.setFile_type(fileType.toLowerCase());
                attachment.setDept_id(ShiroUtils.getDeptId());
                attachment.setDept_name(ShiroUtils.getDeptName());
                attachment.setTable_name(tableName);
                attachment.setType_id(typeId);
                attachment.setUpload_ip(ip);
                attachment.setUpload_user(ShiroUtils.getUserName());
                addDataInfo(attachment);
                systemAttachmentMapper.insert(attachment);
            }else {

                if (type.equals("minio")) { //minio服务
                    if (!MinIoUtils.uploadFile(MemCache.getSystemParameter("ossBucketName"), file, path)) {
                        return Result.failure("500", "上传失败");
                    }
                    return Result.failure("500", "上传失败");
                } else {
                    try {
                        //加个时间戳，方便备份归档文件
                        path = "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/" + newfileName;
                        //文件绝对路径
                        log.info("保存文件路径:" + path + "\n");

                        //创建文件路径
                        File dest = new File(MemCache.getSystemParameter("filePath") + path);
                        //判断文件是否已经存在
                        if (dest.exists()) {
                            log.error("文件已经存在");
                            return Result.failure("500", "文件已经存在");
                        }
                        //判断文件父目录是否存在
                        if (!dest.getParentFile().exists()) {
                            dest.getParentFile().mkdir();
                        }
                        //上传文件
                        file.transferTo(dest); //保存文件

                    } catch (IOException e) {
                        log.error("上传失败", e);
                        return Result.failure("500", "上传失败");
                    }
                }

                log.info("保存文件路径" + MemCache.getSystemParameter("filePath") + path + "\n");

                SystemAttachment attachment = new SystemAttachment();

                try {
                    attachment = Map2EntityUtil.updateModel(attachment, requestMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                attachment.setAuditing(auditing);
                attachment.setFile_id(fileID);
                attachment.setData_id(keyId);
                attachment.setFile_date(new Date());
                attachment.setFile_name(fileName);
                attachment.setFile_path(path);
                attachment.setFile_size(size);
                attachment.setFile_md5(MD5);
                attachment.setFile_type(fileType.toLowerCase());
                attachment.setDept_id(ShiroUtils.getDeptId());
                attachment.setDept_name(ShiroUtils.getDeptName());
                attachment.setTable_name(tableName);
                attachment.setType_id(typeId);
                attachment.setUpload_ip(ip);
                attachment.setUpload_user(ShiroUtils.getUserName());
                addDataInfo(attachment);
                systemAttachmentMapper.insert(attachment);
            }
            DynamicDataSource.clearDataSource();
            //如果funId和updateColumnName都有值，则更新记录字段
            String updateColumnName= (String) requestMap.get("updateColumnName");
            if(StringUtils.isNotEmpty(funId)&&StringUtils.isNotEmpty(updateColumnName)){
                DynamicDataSource.setDataSource(funinfo.getDb_source());
                BaseEntity entity= (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyId);
                if(entity!=null){
                    Map mp=new HashMap();
                    mp.put(updateColumnName,"/file/download?file_id=" + fileID);
                    try {
                        entity=Map2EntityUtil.updateModel(entity,mp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getMapper(tableName).updateByPrimaryKey(entity);
                }
                DynamicDataSource.clearDataSource();
            }


            Map mp=new HashMap();
            mp.put("file_id",fileID);
            mp.put("file_path",path);
            for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
                if(!StringUtils.isEmpty((String)entry.getValue())&& ((String) entry.getValue()).indexOf(fileName)>-1){//字段对应上传文件
                    mp.put("column_name",entry.getKey());
                }
            }
            file_url_list.add(mp);
        }

        return Result.success(file_url_list);
    }

    public ImageUploadVo uploadCK(MultipartFile file,Map<String, Object> requestMap,String ip){
        ImageUploadVo r=new ImageUploadVo();
        List<Map> file_url_list = Lists.newArrayList();
        String funId= (String) requestMap.get("funId");
        String keyId= (String) requestMap.get("keyId");
        String typeId= (String) requestMap.get("typeId");
        String MD5=getMd5(file);
        String size= FileUtils.getPrintSize(file.getSize());
        log.info("上传文件===" + "\n");
        //判断文件是否为空
        if (file.isEmpty()) {
            r.setUploaded(0);
            log.error("500", "上传文件不可为空");
            return r;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String fileType = "";
        if (fileName.indexOf(".") > 0) fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.info("上传的文件名为: " + fileName + "\n");
        fileName = fileName.substring(0, fileName.lastIndexOf(".") - 1);
        String fileID = getKeyUID();
        String newfileName = funId + fileID;
        log.info("（加个时间戳，尽量避免文件名称重复）保存的文件名为: " + newfileName + "\n");
        String type = MemCache.getSystemParameter("fileServerType");;
        if (StringUtils.isEmpty(type)) type = "file";
        String path = newfileName;

        //查重，避免重复上传
        SystemAttachment query=new SystemAttachment();
        query.setFile_md5(MD5);
        List<SystemAttachment> ls=systemAttachmentMapper.select(query);
        if(ls!=null&&ls.size()>0){
            SystemAttachment attachment = ls.get(0);
            log.info("查询到已有相同文件存在，不再保存新文件！"+attachment.getFile_path());

            DynamicDataSource.setDataSource("default");
            ToolFunction funinfo = layoutService.getFunctionInfo(funId);
            String tableName = funinfo != null ? funinfo.getTable_name() : "";
            attachment.setFile_id(fileID);
            attachment.setData_id(keyId);
            attachment.setFile_date(new Date());
            attachment.setFile_name(fileName);
            // attachment.setFile_path(path);  //用已有文件的path
            attachment.setFile_size(size);
            attachment.setFile_md5(MD5);
            attachment.setFile_type(fileType.toLowerCase());
            attachment.setDept_id(ShiroUtils.getDeptId());
            attachment.setDept_name(ShiroUtils.getDeptName());
            attachment.setTable_name(tableName);
            attachment.setType_id(typeId);
            attachment.setUpload_ip(ip);
            attachment.setUpload_user(ShiroUtils.getUserName());
            addDataInfo(attachment);
            systemAttachmentMapper.insert(attachment);
            DynamicDataSource.clearDataSource();
        }else {

            if (type.equals("minio")) { //minio服务
                if (!MinIoUtils.uploadFile(MemCache.getSystemParameter("ossBucketName"), file, path)) {
                    r.setUploaded(0);
                    log.error("500", "上传失败");
                    return r;
                }
                r.setUploaded(0);
                log.error("500", "上传失败");
                return r;
            } else {
                try {
                    //加个时间戳，方便备份归档文件
                    path = "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/" + newfileName;
                    //文件绝对路径
                    log.info("保存文件路径:" + path + "\n");

                    //创建文件路径
                    File dest = new File(MemCache.getSystemParameter("filePath") + path);
                    //判断文件是否已经存在
                    if (dest.exists()) {
                        log.error("文件已经存在");

                        r.setUploaded(0);
                        log.error("500", "文件已经存在");
                        return r;
                    }
                    //判断文件父目录是否存在
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdir();
                    }
                    //上传文件
                    file.transferTo(dest); //保存文件

                } catch (IOException e) {
                    log.error("上传失败", e);

                    r.setUploaded(0);
                    log.error("500", "上传失败");
                    return r;
                }
            }

            log.info("保存文件路径" + MemCache.getSystemParameter("filePath") + path + "\n");
            DynamicDataSource.setDataSource("default");
            ToolFunction funinfo = layoutService.getFunctionInfo(funId);
            String tableName = funinfo != null ? funinfo.getTable_name() : "";

            SystemAttachment attachment = new SystemAttachment();

            try {
                attachment = Map2EntityUtil.updateModel(attachment, requestMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            attachment.setFile_id(fileID);
            attachment.setData_id(keyId);
            attachment.setFile_date(new Date());
            attachment.setFile_name(fileName);
            attachment.setFile_path(path);
            attachment.setFile_size(size);
            attachment.setFile_md5(MD5);
            attachment.setFile_type(fileType.toLowerCase());
            attachment.setDept_id(ShiroUtils.getDeptId());
            attachment.setDept_name(ShiroUtils.getDeptName());
            attachment.setTable_name(tableName);
            attachment.setType_id(typeId);
            attachment.setUpload_ip(ip);
            attachment.setUpload_user(ShiroUtils.getUserName());
            addDataInfo(attachment);
            systemAttachmentMapper.insert(attachment);
            DynamicDataSource.clearDataSource();
        }

        r.setUploaded(1);
        r.setFileName(fileName);
        r.setUrl("/file/download?file_id=" + fileID);
        return r;
    }


    /**
     * 获取上传文件的md5
     *
     * @param file
     * @return
     * @throws IOException
     */
    public String getMd5(MultipartFile file) {

        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            return hashString;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return null;

    }


    public Result<?> deleteFile(String file_id){
        DynamicDataSource.setDataSource("default");
        SystemAttachment entity = systemAttachmentMapper.selectByPrimaryKey(file_id);
        DynamicDataSource.clearDataSource();

        String type= MemCache.getSystemParameter("fileServerType");
        if(StringUtils.isEmpty(type)) type="file";
        if(type.equals("minio")){ //minio服务
            String filePath=entity.getFile_path();//格式/bucketName/filename
            if(filePath.startsWith("/")) filePath=filePath.substring(1,filePath.length()-1);
            String[] names=filePath.split("/");
            MinIoUtils.removeFile(names[0],names[1]);

        }else{//其他增加实现，没有实现默认用本地文件file
            File file= new File(MemCache.getSystemParameter("filePath")+entity.getFile_path());
            if(file.exists()) file.delete();
        }
        systemAttachmentMapper.deleteByPrimaryKey(file_id);
        return Result.success();
    }


    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String uriStr=(String)requestMap.get("uri");
        File downloadFile=null;
        SystemAttachment file =null;
        if(StringUtils.isNotEmpty(uriStr)){
            if (uriStr.startsWith("file://")){
                uriStr = uriStr.substring(7);
                downloadFile =new File(uriStr);
            }else if (uriStr.startsWith("hbase://")){
                try {
                    downloadFile= new File(new URI(uriStr));
                } catch (URISyntaxException e) {
                    log.error(e.getMessage(),e);
                    returnHTML(response, "获取文件失败！"+uriStr);
                    return;
                }
            }
        }else {
            String file_id = (String) requestMap.get("file_id");
            String file_path = (String) requestMap.get("file_path");//也可以是文件链接
            if (StringUtils.isEmpty(file_id)&&StringUtils.isEmpty(file_path)) {
                returnHTML(response, "参数为空或缺少关键参数！");
                return;
            }
            DynamicDataSource.setDataSource("default");
            if(StringUtils.isNotEmpty(file_id)){
                file = systemAttachmentMapper.selectByPrimaryKey(file_id);
            }else if(StringUtils.isNotEmpty(file_path)){
                SystemAttachment query=new SystemAttachment();
                query.setFile_path(file_path);
                List<SystemAttachment> fls= systemAttachmentMapper.select(query);
                if(fls!=null&&fls.size()>0)
                    file=fls.get(0);
            }
            DynamicDataSource.clearDataSource();
            if (file == null) {
                returnHTML(response, "参数错误，获取文件失败！");
                return;
            }
            downloadFile = getFile(file);
            if (downloadFile == null || !downloadFile.exists()) {
                returnHTML(response, "文件不存在或已被删除，获取文件失败！");
                return;
            }
        }
        String fileName =downloadFile.getName();
        if(file!=null) fileName = file.getFile_name().indexOf(".")>0?file.getFile_name():file.getFile_name()+"."+file.getFile_type();
        //  response.setContentType("application/force-download");// 设置强制下载不打开
        response.setContentType("application/octet-stream");
        // IE使用URLEncoder
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        String strHeader = "";
        if(userAgent.contains("firefox")){
            strHeader = "attachment;filename="+new String((fileName).getBytes("utf-8"), "ISO8859-1");
        }else if (userAgent.contains("windows")) {
            strHeader = "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8");
            // 其它使用转iso
        } else {
            strHeader = "attachment;filename="+ new String((fileName).getBytes("utf-8"), "ISO8859-1");
        }
        response.addHeader("Content-Disposition", strHeader);// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(downloadFile);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void downloaRanges(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String uriStr=(String)requestMap.get("uri");
        File downloadFile=null;
        SystemAttachment file =null;
        if(StringUtils.isNotEmpty(uriStr)){
            if (uriStr.startsWith("file://")){
                uriStr = uriStr.substring(7);
                downloadFile =new File(uriStr);
            }else if (uriStr.startsWith("hbase://")){
                try {
                    downloadFile= new File(new URI(uriStr));
                } catch (URISyntaxException e) {
                    log.error(e.getMessage(),e);
                    returnHTML(response, "获取文件失败！"+uriStr);
                    return;
                }
            }
        }else {
            String file_id = (String) requestMap.get("file_id");
            if (file_id == null || file_id.equals("")) {
                returnHTML(response, "参数为空或缺少关键参数！");
                return;
            }
            DynamicDataSource.setDataSource("default");
            file = systemAttachmentMapper.selectByPrimaryKey(file_id);
            DynamicDataSource.clearDataSource();
            if (file == null) {
                returnHTML(response, "参数错误，获取文件失败！");
                return;
            }
            downloadFile = getFile(file);
            if (downloadFile == null || !downloadFile.exists()) {
                returnHTML(response, "文件不存在或已被删除，获取文件失败！");
                return;
            }
        }
        String fileName =downloadFile.getName();
        if(file!=null) fileName = file.getFile_name().indexOf(".")>0?file.getFile_name():file.getFile_name()+"."+file.getFile_type();

        // 要下载的文件大小
        long fileLength = downloadFile.length();
        // 已下载的文件大小
        long pastLength = 0;
        // 是否快车下载，否则为迅雷或其他
        boolean isFlashGet = true;
        // 用于记录需要下载的结束字节数（迅雷或其他下载）
        long lenEnd = 0;
        // 用于记录客户端要求下载的数据范围字串
        String rangeBytes = request.getHeader("Range");
        //用于随机读取写入文件
        RandomAccessFile raf = null;
        OutputStream os = null;
        OutputStream outPut = null;
        byte b[] = new byte[1024];
        // 如果客户端下载请求中包含了范围
        if (null != rangeBytes)
        {
            // 返回码 206
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
            // 判断 Range 字串模式
            if (rangeBytes.indexOf('-') == rangeBytes.length() - 1)
            {
                // 无结束字节数，为快车
                isFlashGet = true;
                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                pastLength = Long.parseLong(rangeBytes.trim());
            }
            else
            {
                // 迅雷下载
                isFlashGet = false;
                String startBytes = rangeBytes.substring(0,
                        rangeBytes.indexOf('-'));
                String endBytes = rangeBytes.substring(
                        rangeBytes.indexOf('-') + 1, rangeBytes.length());
                // 已下载文件段
                pastLength = Long.parseLong(startBytes.trim());
                // 还需下载的文件字节数(从已下载文件段开始)
                lenEnd = Long.parseLong(endBytes);
            }
        }
        // 通知客户端允许断点续传，响应格式为：Accept-Ranges: bytes
        response.setHeader("Accept-Ranges", "bytes");
        // response.reset();
        // 如果为第一次下载，则状态默认为 200，响应格式为： HTTP/1.1 200 ok
        if (0 != pastLength)
        {
            // 内容范围字串
            String contentRange = "";
            // 响应格式
            // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]||[文件的总大小]
            if (isFlashGet)
            {
                contentRange = new StringBuffer("bytes")
                        .append(new Long(pastLength).toString()).append("-")
                        .append(new Long(fileLength - 1).toString())
                        .append("/").append(new Long(fileLength).toString())
                        .toString();
            }
            else
            {
                contentRange = new StringBuffer(rangeBytes).append("/")
                        .append(new Long(fileLength).toString()).toString();
            }
            response.setHeader("Content-Range", contentRange);
        }
        response.setHeader("Content-Disposition",
                "attachment;filename=" + fileName + "");
        // 响应的格式是:
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Length", String.valueOf(fileLength));
        try
        {
            os = response.getOutputStream();
            outPut = new BufferedOutputStream(os);
            raf = new RandomAccessFile(downloadFile, "r");
            // 跳过已下载字节
            raf.seek(pastLength);
            if (isFlashGet)
            {
                // 快车等
                int n = 0;
                while ((n = raf.read(b, 0, 1024)) != -1)
                {
                    outPut.write(b, 0, n);
                }
            }
            else
            {
                // 迅雷等
                while (raf.getFilePointer() < lenEnd)
                {
                    outPut.write(raf.read());
                }
            }
            outPut.flush();
        }
        catch (IOException e)
        {
            /**
             * 在写数据的时候 对于 ClientAbortException 之类的异常
             * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。 尤其是对于迅雷这种吸血的客户端软件。
             * 明明已经有一个线程在读取 bytes=1275856879-1275877358，
             * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
             * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
             * 所以，我们忽略这种异常
             */
        }
        finally
        {
            if(outPut != null)
            {
                outPut.close();
            }
            if(raf != null)
            {
                raf.close();
            }
        }
    }



    /*
     * 返回页面内容模版
     */
    private void returnHTML(HttpServletResponse response, String msg) throws IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        StringBuilder rtMsg = new StringBuilder(msg);
        out.write(rtMsg.toString().getBytes("UTF-8"));
        out.close();
    }

    private File getFile(SystemAttachment file) throws IOException {
        String type=MemCache.getSystemParameter("fileServerType");
        if(StringUtils.isEmpty(type)) type="file";
        if(type.equals("minio")){ //minio服务
            String filePath=file.getFile_path();//格式/bucketName/filename
            if(filePath.startsWith("/")) filePath=filePath.substring(1,filePath.length()-1);
            String[] names=filePath.split("/");
            if(names.length!=2) return null;
            InputStream in=   MinIoUtils.getFileInputStream(names[0],names[1]);
            OutputStream os = null;
            File downloadFile=new File(MemCache.getSystemParameter("filePath")+"/"+file.getFile_id());
            if(downloadFile.exists()) return downloadFile;
            try {
                os = new FileOutputStream(downloadFile);
                int len = 0;
                byte[] buffer = new byte[8192];

                while ((len = in.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                os.close();
                in.close();
            }
            return downloadFile;

        }else{//其他增加实现，没有实现默认用本地文件file
            return new File(MemCache.getSystemParameter("filePath")+file.getFile_path());
        }
    }



    @Data
    public static class ImageUploadVo {
        private int uploaded;
        private String fileName;
        private String url;
    }
}
