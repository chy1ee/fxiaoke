package com.chylee.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Model {
    //表名
    private String tableName;
    //列名数组
    private String[] colNames;
    //列名类型数组
    private String[] colTypes;
    //列名大小数组
    private int[] colSizes;
    //列名注释
    private Map colNamesComment = new HashMap();
    //是否需要导入包java.util.*
    private boolean needUtil = false;
    //是否需要导入包java.sql.*
    private boolean needSql = false;
    //是否需要导入包java.math.BigDecimal
    private boolean needBigDecimal = false;
    //是否创建EntityHelper
    private boolean needEntityHelper = true;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String SQL = "SELECT * FROM ";// 数据库操作

    // 数据库配置信息
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/fxiaoke?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String NAME = "root";
    private static final String PASS = "123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //指定实体生成所在包的路径
    private static String basePath = new File("").getAbsolutePath();
    //指定包名
    private static String packageOutPath = "com.chylee.code";
    //作者名字
    private String authorName = "chylee";
    //指定需要生成的表的表名，全部生成设置为null
    private String[] generateTables = new String[]{"fxk_status_report"};
    //主键
    private static String pk;

    /**
     * @description 生成class的所有内容
     */
    private String parse() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + packageOutPath + ";\r\n");
        sb.append("\r\n");
        // 判断是否导入工具包
        if (needUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (needSql) {
            sb.append("import java.sql.*;\r\n");
        }

        for (int i = 0; i < colNames.length; i++) {
            String hasbd = sqlType2JavaType(colTypes[i]);
            if(hasbd =="BigDecimal" || "BigDecimal".equals(hasbd)) {needBigDecimal=true;}
        }
        if(needBigDecimal) {
            sb.append("import java.math.BigDecimal;\r\n");
        }
        // 注释部分
        sb.append("/**\r\n");
        sb.append(" * table name:  " + tableName + "\r\n");
        sb.append(" * author name: " + authorName + "\r\n");
        sb.append(" * create time: " + SDF.format(new Date()) + "\r\n");
        sb.append(" */ \r\n");
        // 实体部分
        String classExtends = "";
        if(needEntityHelper) {
            classExtends=" extends EntityHelper";
        }
        sb.append("public class " + under2camel(tableName, true) + classExtends + "{\r\n\r\n");

        processAllAttrs(sb);// 属性
        sb.append("\r\n");
        processConstructor(sb);//构造函数
        processAllMethod(sb);// get set方法
        processToString(sb);
        if(needEntityHelper) {
            processEntityHelper(sb,pk);
        }
        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * @param sb
     * @description 生成所有成员变量及注释
     * @author paul
     * @version V1.0
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            if(colNamesComment.get(colNames[i])!=null &&!"".equals(colNamesComment.get(colNames[i]))) {
                sb.append("\t/*"+colNamesComment.get(colNames[i])+"*/\r\n");
            }
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colNames[i] + ";\r\n");
        }
    }
    /**
     * EntityHelper
     * @param sb
     * @param pk
     */
    private void processEntityHelper(StringBuffer sb,String pk) {
        sb.append("\t@Override\r\n");
        sb.append("\tpublic String getPrimaryKey() {\r\n");
        sb.append("\t\treturn \""+pk+"\";\r\n");
        sb.append("\t}\r\n");
    }
    /**
     * 重写toString()方法
     * @param sb
     */
    private void processToString(StringBuffer sb) {
        sb.append("\t@Override\r\n\tpublic String toString() {\r\n");
        sb.append("\t\treturn \"" +tableName + "[\" + \r\n");
        for (int i = 0; i < colNames.length; i++) {
            if (i != 0)
                sb.append("\t\t\t\", ");
            if (i == 0)
                sb.append("\t\t\t\"");
            sb.append(colNames[i] + "=\" + "
                    + colNames[i]).append(" + \r\n");
            if (i == colNames.length - 1) {
                sb.append("\t\t\t\"]\";\r\n");
            }
        }
        sb.append("\t}\r\n");
    }
    /**
     * 构造函数
     * @param sb
     */
    private void processConstructor(StringBuffer sb) {
        StringBuffer p = new StringBuffer();
        StringBuffer v = new StringBuffer();
        for(int i = 0; i < colNames.length; i++) {
            p.append(sqlType2JavaType(colTypes[i])+" "+colNames[i]);
            if(i!=colNames.length-1) {
                p.append(",");
            }
            v.append("\t\tthis."+colNames[i]+"="+colNames[i]+";\r\n");
        }
        //无参数构造函数
        sb.append("\tpublic "+under2camel(tableName,true)+"() {\r\n");
        sb.append("\t\tsuper();\r\n");
        sb.append("\t}\r\n");
        //带参构造函数
        sb.append("\tpublic "+under2camel(tableName,true)+"("+p.toString()+") {\r\n");
        sb.append(v.toString());
        sb.append("\t}\r\n");
    }

    /**
     * @param sb
     * @description 生成所有get/set方法
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\tpublic void set" + initCap(colNames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " "
                    + colNames[i] + "){\r\n");
            sb.append("\t\tthis." + colNames[i] + "=" + colNames[i] + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initCap(colNames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colNames[i] + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
     * @param str 传入字符串
     * @return
     * @description 将传入字符串的首字母转成大写
     */
    private String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z')
            ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    /**
     * 功能：下划线命名转驼峰命名
     * @param s
     * @param fistCharToUpperCase 首字母是否大写
     * @author 呐喊
     * @return
     */
    private String under2camel(String s,boolean fistCharToUpperCase) {
        String separator = "_";
        String under="";
        s = s.toLowerCase().replace(separator, " ");
        String sarr[]=s.split(" ");
        for(int i=0;i<sarr.length;i++)
        {
            String w=sarr[i].substring(0,1).toUpperCase()+sarr[i].substring(1);
            under +=w;
        }
        if(!fistCharToUpperCase) {
            under = under.substring(0,1).toLowerCase()+under.substring(1);
        }
        return under;
    }

    /**
     * @return
     * @description 查找sql字段类型所对应的Java类型
     */
    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")|| sqlType.equalsIgnoreCase("longtext")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }else if (sqlType.equalsIgnoreCase("decimal")) {
            return "BigDecimal";
        }
        return null;
    }
    /**
     * 功能：获取并创建实体所在的路径目录
     * @return
     */
    private static String pkgDirName() {
        String dirName = basePath + "/src/" + packageOutPath.replace(".", "/");
        File dir = new File(dirName);
        if (!dir.exists()) {dir.mkdirs();System.out.println("mkdirs dir 【" + dirName + "】");}
        return dirName;
    }
    /**
     * 生成EntityHelper
     */
    private void EntityHelper() {
        String dirName = Model.pkgDirName();
        String javaPath = dirName + "/EntityHelper.java";
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageOutPath + ";\r\n");
            sb.append("\r\n");
            sb.append("public abstract class EntityHelper{\r\n\r\n");
            sb.append("\tpublic abstract String getPrimaryKey();\r\n");
            sb.append("\r\n");
            sb.append("}\r\n");
            FileWriter fw = new FileWriter(javaPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            if (pw != null){pw.close();}
            System.out.println("create class 【EntityHelper】");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @description 生成方法
     */
    private void generate() throws Exception {
        //与数据库的连接
        Connection con;
        PreparedStatement pStemt = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, NAME, PASS);
        System.out.println("connect database success..."+con);
        //获取数据库的元数据
        DatabaseMetaData db = con.getMetaData();
        //是否有指定生成表，有指定则直接用指定表，没有则全表生成
        List<String> tableNames = new ArrayList<>();
        if (generateTables == null) {
            //从元数据中获取到所有的表名
            ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
            while (rs.next()) tableNames.add(rs.getString(3));
        } else {
            for (String tableName : generateTables) tableNames.add(tableName);
        }
        if(needEntityHelper) {
            EntityHelper();
        }
        String tableSql;
        PrintWriter pw = null;
        for (int j = 0; j < tableNames.size(); j++) {
            tableName = tableNames.get(j);
            tableSql = SQL + tableName;
            pStemt = con.prepareStatement(tableSql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            ResultSet rsk = con.getMetaData().getPrimaryKeys(con.getCatalog().toLowerCase(), null, tableName);
            if (rsk.next()) {
                String primaryKey = rsk.getString("COLUMN_NAME");
                pk=primaryKey;
            }
            int size = rsmd.getColumnCount();
            colNames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            //获取所需的信息
            for (int i = 0; i < size; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                if (colTypes[i].equalsIgnoreCase("datetime"))
                    needUtil = true;
                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text"))
                    needSql = true;
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            //获取字段注释
            ResultSet rsComment = pStemt.executeQuery("show full columns from " + tableName);
            while (rsComment.next()) {
                colNamesComment.put(rsComment.getString("Field"), rsComment.getString("Comment"));
            }
            //解析生成实体java文件的所有内容
            String content = parse();
            //输出生成文件
            String dirName = Model.pkgDirName();
            String javaPath = dirName + "/" + under2camel(tableName, true) + ".java";
            System.out.println(javaPath);
            FileWriter fw = new FileWriter(javaPath);
            pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            System.out.println("create class 【" + tableName + "】");
        }
        if (pw != null)
            pw.close();
    }

    public static void main(String[] args) {
        Model instance = new Model();
        //instance.basePath=""; //指定生成的位置,默认是当前工程
        try {
            instance.generate();
            System.out.println("generate Entity to classes successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
