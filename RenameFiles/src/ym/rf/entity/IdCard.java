package ym.rf.entity;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: WaitingForYou
 * Date: 13-9-21
 * Time: 上午9:21
 */
public class IdCard {

    private String id;
    private String location;
    private String birthday;
    private String gender;
    private String birthYear;
    private String birthMonth;
    private String birthDay;

    /**
     * 根据身份证号查询相关信息。
     *
     * @param id 身份证号
     */
    public IdCard query(String id) {
        String info = null;
        try {
            info = getStringFromUrl(
                    "http://www.youdao.com/smartresult-xml/search.s?type=id&q="
                            + id, "gbk");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (info == null || info.contains("<smartresult/>")) {
            return null;
        }
//		System.out.println(info);
        IdCard idCard = parseXML(info);
//		System.out.println(idCard);
        return idCard;
    }

    /**
     * 根据URL名得到输入流。
     *
     * @param urlStr URL名。
     * @return 得到的输入流。
     * @throws MalformedURLException 如果字符串指定未知协议。
     * @throws IOException           如果发生 I/O 错误。
     */
    private String getStringFromUrl(String urlStr, String charsetName) throws MalformedURLException, IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        InputStream in = urlConn.getInputStream();
        String string = InputStreamToString(in, charsetName);
        return string;
    }

    /**
     * 将InputStream转换成String
     *
     * @param in
     * @param charsetName 字符集名
     * @return
     */
    private String InputStreamToString(InputStream in, String charsetName) {

        if (in == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String temp = null;
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, charsetName));
            while ((temp = bf.readLine()) != null) {
                sb.append(temp).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public class MyContentHandler extends DefaultHandler {

        private IdCard idCard;
        private String tagName;

        public MyContentHandler(IdCard idCard) {
            this.idCard = idCard;
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            String tmp = new String(ch, start, length);
            if (tagName.equals("code")) {
                idCard.setId(tmp);
            } else if (tagName.equals("location")) {
                idCard.setLocation(tmp);
            } else if (tagName.equals("birthday")) {
                idCard.setBirthYear(tmp.substring(0, 4));
                idCard.setBirthMonth(tmp.substring(4, 6));
                idCard.setBirthDay(tmp.substring(6, 8));
            } else if (tagName.equals("gender")) {
                idCard.setGender(tmp);
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            tagName = "";
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            this.tagName = localName;
            if ("".equals(localName)) {
                this.tagName = qName;
            }
//		System.out.println("uri:" + uri + ", localName:" + localName + ", qName:" + qName);
        }

        public IdCard getIdCard() {
            return idCard;
        }
    }

    /**
     * 得析得到的XML字符串的信息
     *
     * @param info
     * @return
     */
    private IdCard parseXML(String info) {
        IdCard idCard = new IdCard();

        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            MyContentHandler contentHandler = new MyContentHandler(idCard);
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(new StringReader(info)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static void main(String[] args) {
        IdCard idCard = new IdCard();
        idCard.setId("440923198911043434");
        idCard.query("440923198911043434");
    }
}