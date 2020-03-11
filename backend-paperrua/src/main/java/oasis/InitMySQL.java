package oasis;

import com.csvreader.CsvReader;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;


public class InitMySQL {

    public ArrayList<Integer> removeDuplicate(ArrayList<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(i).equals(list.get(j))) {
                    list.remove(j);
                }
            }
        }

//返回结果

        return list;
    }

    public Connection initial_con(String DB_URL, String USER, String PASS) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ArrayList<Integer> insertAffiliAndAuthor(CsvReader cr, Connection conn, ArrayList<Integer> authorIdList, ArrayList<Integer> affilicationIdList) {
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        int affiliationId;
        try {
            String[] affilicationList = cr.get(2).split(";");
            String[] authorList = cr.get(1).split(";");


            //插入机构和作者信息
            for (int i = 0; i < affilicationList.length; i++) {
                if (affilicationList[i] != "" && i < authorList.length) {
                    stmt = conn.createStatement();
                    sql = "select affiliation_id from affiliation_info where affiliation_name=\"" + affilicationList[i].trim() + "\"";
                    rs = stmt.executeQuery(sql);
                    if (rs.next() == false) {
                        //如果不存在机构信息，则插入
                        stmt = conn.createStatement();
                        sql = "insert into affiliation_info (affiliation_name) values(\"" + affilicationList[i].trim() + "\")";
                        stmt.execute(sql);
                        //获取机构id
                        stmt = conn.createStatement();
                        sql = "select affiliation_id from affiliation_info where affiliation_name=\"" + affilicationList[i].trim() + "\"";
                        rs = stmt.executeQuery(sql);
                        rs.next();
                    }
                    affiliationId = rs.getInt("affiliation_id");
                    affilicationIdList.add(affiliationId);


                    //不存在作者信息则插入
                    stmt = conn.createStatement();
                    sql = "select author_id from author_info where author_name=\"" + authorList[i].trim() + "\"" + "and affiliation_id=" + affiliationId;
                    rs = stmt.executeQuery(sql);
                    if (rs.next() == false) {
                        stmt = conn.createStatement();
                        sql = "insert into author_info (author_name,affiliation_id) values (\"" + authorList[i].trim() + "\"," + affiliationId + ")";
                        stmt.execute(sql);
                        stmt = conn.createStatement();
                        sql = "select author_id from author_info where author_name=\"" + authorList[i].trim() + "\"" + "and affiliation_id=" + affiliationId;
                        rs = stmt.executeQuery(sql);
                        rs.next();

                    }

                    authorIdList.add(rs.getInt("author_id"));

                }
            }

            affilicationIdList = removeDuplicate(affilicationIdList);
            authorIdList = removeDuplicate(authorIdList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorIdList;

    }

    public ArrayList<Integer> insertKeyword(CsvReader cr, Connection conn, ArrayList<Integer> keyWordIdList) {
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        double randd = 0.0;
        double randi = 0;
        try {
            keyWordIdList = new ArrayList<>();
            String[] keywordsList = cr.get(16).split(";");
            for (int i = 0; i < keywordsList.length; i++) {
                randd = Math.random();
                randi = (int) (randd * 10000);
                if (keywordsList[i] != "") {
                    stmt = conn.createStatement();
                    sql = "select * from keyword_info where keyword=\"" + keywordsList[i].trim() + "\"";
                    rs = stmt.executeQuery(sql);
                    if (rs.next() == false) {
                        stmt = conn.createStatement();
                        sql = "insert into keyword_info (keyword,keyword_search_count) values(\"" + keywordsList[i].trim() + "\"," + randi + ")";
                        stmt.execute(sql);
                        stmt = conn.createStatement();
                        sql = "select keyword_id from keyword_info where keyword=\"" + keywordsList[i].trim() + "\"";
                        rs = stmt.executeQuery(sql);
                        rs.next();
                    }
                    keyWordIdList.add(rs.getInt("keyword_id"));


                }
            }

            keyWordIdList = removeDuplicate(keyWordIdList);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return keyWordIdList;
    }

    public int insertPublication(CsvReader cr, Connection conn, String type) {
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        int publicationId = -1;
        try {
            String publicationTitle = cr.get(3).trim();
            String publicationYear = cr.get(5).trim();
            stmt = conn.createStatement();
            sql = "select publication_id from publication_info where publication_title=\"" + publicationTitle + "\" and publication_year=\"" + publicationYear + "\"";
            rs = stmt.executeQuery(sql);
            if (rs.next() == false) {
                //如果没有会议信息则插入
                stmt = conn.createStatement();
                sql = "insert into publication_info (publication_title, publication_year,publication_type) values(\"" + publicationTitle + "\",\"" + publicationYear + "\",\"" + type + "\")";
                stmt.execute(sql);
                stmt = conn.createStatement();
                sql = "select publication_id from publication_info where publication_title=\"" + publicationTitle + "\" and publication_year=\"" + publicationYear + "\"";
                rs = stmt.executeQuery(sql);
                rs.next();
            }
            publicationId = rs.getInt("publication_id");
        } catch (Exception e) {

        }
        return publicationId;
    }

    public int insertAtricle(CsvReader cr, Connection conn, int publicationId) {
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        int articleId = 0;
        try {
            String docTitle = cr.get(0).trim().replace("\"", "'");
            int startPage = 0;
            if (cr.get(8).trim().length() != 0) {
                startPage = Integer.parseInt(cr.get(8).trim());
            }
            int endPage = 0;
            if (cr.get(9).trim().length() != 0) {
                endPage = Integer.parseInt(cr.get(9).trim());
            }
            String docAbstract = cr.get(10).trim().replace("\"", "'");
            String doi = cr.get(13).trim();
            String pdfLink = cr.get(15).trim();
            int referenceCount = 0;
            if (cr.get(22).trim().length() != 0) {
                referenceCount = Integer.parseInt(cr.get(22).trim());
            }
            int citation = 0;
            if (cr.get(21).trim().length() != 0) {
                citation = Integer.parseInt(cr.get(21).trim());
            }
            String publisher = cr.get(27).trim();
            String docIdentifier = cr.get(28).trim();

            stmt = conn.createStatement();
            sql = "select doc_id from article_info where doc_title=\"" + docTitle + "\" and publication_id=" + publicationId;
            rs = stmt.executeQuery(sql);


            if (rs.next() == false) {
                //如果不存在论文信息则插入
                stmt = conn.createStatement();
                sql = "insert into article_info (doc_title, publication_id, startpage, endpage, doc_abstract, pdf_link, doi, " +
                        "reference_count, citation, publisher, doc_identifier,search_count) values (\"" + docTitle + "\"," + publicationId + "," + startPage + "," + endPage + ",\"" + docAbstract + "\",\"" + pdfLink +
                        "\",\"" + doi + "\"," + referenceCount + "," + citation + ",\"" + publisher + "\",\"" + docIdentifier + "\"," + 0 + ")";
                stmt.execute(sql);
                //获取文章id
                stmt = conn.createStatement();
                sql = "select doc_id from article_info where doc_title=\"" + docTitle + "\"";
                rs = stmt.executeQuery(sql);
                rs.next();
            }
            articleId = rs.getInt("doc_id");
        } catch (Exception e) {

        }
        return articleId;
    }


    public void readFromCsv(String DB_URL, String USER, String PASS, String path, String publicationType) {

        try {
            Connection conn = initial_con(DB_URL, USER, PASS);
            CsvReader cr = null;
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream in = classPathResource.getInputStream();
            cr = new CsvReader(in, ',', Charset.forName("GBK"));
            ArrayList<Integer> authorIdList;
            ArrayList<Integer> keywordIdList;
            ArrayList<Integer> affiliationIdList;
            cr.readHeaders();
            while (cr.readRecord()) {
                //不处理非论文项
                if (cr.get(1) == "") {
                    continue;
                }
                authorIdList = new ArrayList<>();
                keywordIdList = new ArrayList<>();
                affiliationIdList = new ArrayList<>();
                insertAffiliAndAuthor(cr, conn, authorIdList, affiliationIdList);
                keywordIdList = insertKeyword(cr, conn, keywordIdList);
                int pubId = insertPublication(cr, conn, publicationType);
                int articleId = insertAtricle(cr, conn, pubId);


                Statement stmt = null;
                String sql = null;
                ResultSet rs = null;
                for (int i = 0; i < affiliationIdList.size(); i++) {
                    stmt = conn.createStatement();
                    sql = "select * from article_to_affiliation where doc_id=" + articleId + " and affiliation_id=" + affiliationIdList.get(i);
                    rs = stmt.executeQuery(sql);
                    if (rs.next() == false) {
                        stmt = conn.createStatement();
                        sql = "insert into article_to_affiliation (doc_id, affiliation_id) values (" + articleId + "," + affiliationIdList.get(i) + ")";
                        stmt.execute(sql);
                    }
                }

                for (int i = 0; i < authorIdList.size(); i++) {
                    stmt = conn.createStatement();
                    sql = "select * from article_to_author where doc_id=" + articleId + " and author_id=" + authorIdList.get(i);
                    rs = stmt.executeQuery(sql);
                    if (rs.next() == false) {
                        stmt = conn.createStatement();
                        sql = "insert into article_to_author (doc_id, author_id) values (" + articleId + "," + authorIdList.get(i) + ")";
                        stmt.execute(sql);
                    }
                }

                for (int i = 0; i < keywordIdList.size(); i++) {
                    stmt = conn.createStatement();
                    sql = "select * from article_to_keyword where doc_id=" + articleId + " and keyword_id=" + keywordIdList.get(i);
                    rs = stmt.executeQuery(sql);
                    if (rs.next() == false) {
                        stmt = conn.createStatement();
                        sql = "insert into article_to_keyword (doc_id, keyword_id) values (" + articleId + "," + keywordIdList.get(i) + ")";
                        stmt.execute(sql);
                    }
                }

            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start() {
        readFromCsv("jdbc:mysql://localhost:3306/oasis?serverTimezone=UTC&useSSL=false", "root", "123456", "datasource/ase13_15_16_17_19.csv", "ase");
        readFromCsv("jdbc:mysql://localhost:3306/oasis?serverTimezone=UTC&useSSL=false", "root", "123456", "datasource/icse15_16_17_18_19.csv", "icse");
    }

    public static void main(String[] args) {
        System.out.println("用于视频演示的提交3");
        InitMySQL initMySQL = new InitMySQL();
        initMySQL.start();
    }
}
