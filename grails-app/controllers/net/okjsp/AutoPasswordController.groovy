package net.okjsp

import com.estorm.framework.util.HttpConnection
import com.estorm.framework.util.HttpParameter
import com.estorm.framework.util.OTPUtiles
import grails.transaction.Transactional
import net.okjsp.encoding.OldPasswordEncoder
import org.codehaus.groovy.grails.web.json.JSONObject

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Transactional
class AutoPasswordController {

    def grailsApplication

    def springSecurityService

    def randomService

    def autoCheck() {

        def config = grailsApplication.config

        String cdtime = params.cdtime
        String sessionid = session.getId()

        String url = "http://localhost:11040/otp/mobileAction"
        HttpParameter params = new HttpParameter()
                .add("sessionid", sessionid)
                .add("subcustom_code", config.autoPassword.corpId)

        String result = new HttpConnection().sendPost(url, params)
        JSONObject json = new JSONObject(result)

        json.put("resttime", (Integer.parseInt(cdtime) - (int)(System.currentTimeMillis()/1000)))

        render json
    }

    def cancelCD() {

        def config = grailsApplication.config


        String corp_user_id = getUserInfo(params.corp_user_id)
        if(corp_user_id == null) {
            render "{\"result\":false, \"code\": \"001.4\", \"msg\":\"미등록\", \"data\":\"\"}"
            return
        }


        String token = getTokenForOneTime()


        String url = "http://localhost:11040/otp/rest/adapter/auth/cancelROTP"

        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("token", token)
                .add("corp_user_id", corp_user_id)
                .add("session_id", session.getId())

        String result = new HttpConnection().sendPost(url, params)

        render "{\"result\":true, \"code\": \"000.0\", \"msg\":\"성공\", \"data\":\"\"}"
    }

    def checkID() {

        def config = grailsApplication.config
        String client_ip = request.getRemoteAddr()

        String server_ip = "127.0.0.1"; // 등록시 받은 릴레이서버ID
        int login_type = 0; // 0: ServicePassword(sms, backup) 2" userPassword


        String corp_user_id = getUserInfo(params.corp_user_id)
        if(corp_user_id == null) {
            render "{\"result\":false, \"code\": \"001.4\", \"msg\":\"미등록\", \"data\":\"\"}"
            return
        }

        int session_term = Integer.parseInt(params.session_term)
        String service_type = params.service_type == null ? "service_password" : params.service_type

        int auth_type = 0
        if ("sms".equals(service_type)) {
            auth_type = 2
        } else if ("backup".equals(service_type)) {
            auth_type = 1
        }

        String token = getTokenForOneTime()


        String url = "http://localhost:11040/otp/rest/adapter/auth/getROTP"

        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("token", token)
                .add("corp_user_id", corp_user_id)
                .add("server_ip", server_ip)
                .add("client_ip", client_ip)
                .add("server_type", "0")
                .add("session_id", session.getId())
                .add("sessionterm", (session_term  * 1000)+"")
                .add("corp_code",  config.autoPassword.corpId)
                .add("service_name", URLEncoder.encode("테스트로그인", "utf-8"))
                .add("auth_type", auth_type + "")
                .add("sso", "N")
                .add("ahead", "1")


        String result = new HttpConnection().sendPost(url, params)
        JSONObject json = new JSONObject(result)


        if (json.getBoolean("result")) {
            JSONObject datajson = json.getJSONObject("data");
            datajson.put("login_type", "0").put("use_sms_yn", "N").put("service_type", service_type);
            //.put("backup_otp_cnt", 0);
        }

        render json

    }

    def cancelSession() {

        session.invalidate()
        render "{\"result\":true, \"code\": \"000.0\", \"msg\":\"성공\", \"data\":\"\"}"

    }

    def checkAdded() {

        User user = User.findByOid(params.corp_user_id)

        render "{\"result\":true, \"msg\":\"OK\", \"code\": \"000.0\", \"data\":{\"is_added\":${user != null}}}"

    }

    def checkServiceOTP() {

        def config = grailsApplication.config

        String user_id = params.corp_user_id		//로그인 페이지의 AutoPassword 사용자 아이디 입력 필도
        String user_otp = params.user_otp


        String corp_user_id = getUserInfo(user_id)
        if(corp_user_id == null) {
            render "{\"result\":false, \"code\": \"001.4\", \"msg\":\"미등록\", \"data\":\"\"}"
            return
        }


        String token = getTokenForOneTime()

        String url = "http://localhost:11040/otp/rest/adapter/auth/checkOTP"
        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("token", token)
                .add("corp_user_id", corp_user_id)
                //.add("user_id", corp_user_id)
                .add("session_yn", "Y")
                .add("otp_token", user_otp)
                .add("policy", "0")
                .add("policy_contry", "KO");
        String result = new HttpConnection().sendPost(url, params);
        JSONObject json = new JSONObject(result);
        if (json.getBoolean("result")) {
            session.setAttribute("logined", user_id);
        }

        render "{\"result\":${json.getBoolean("result")}, \"code\": \"${json.getString("code")}\", \"msg\":\"${json.getString("msg")}\", \"data\":\"\"}"
    }

    def checkServiceSecureOTP() {


        def config = grailsApplication.config

        String user_id = params.corp_user_id		//로그인 페이지의 AutoPassword 사용자 아이디 입력 필도
        String user_otp = params.user_otp


        String corp_user_id = getUserInfo(user_id)
        if(corp_user_id == null) {
            render "{\"result\":false, \"code\": \"001.4\", \"msg\":\"미등록\", \"data\":\"\"}"
            return
        }

        String url = "http://localhost:11040/otp/rest/adapter/auth/checkSecureOTP";
        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("token", config.autoPassword.managerToken)
                .add("corp_user_id", corp_user_id)
                .add("random", session.getId())
            //.add("user_id", corp_user_id)
                .add("session_yn", "Y")
                .add("otp_token", user_otp)
                .add("policy", "0")
                .add("policy_contry", "KO");
        String result = new HttpConnection().sendPost(url, params);

        JSONObject json = new JSONObject(result);
        if (json.getBoolean("result")) {
            JSONObject data =  json.getJSONObject("data")
            String hash = data.getString("hash");
            String realresult = data.getString("data")

            json = new JSONObject(realresult);

            if (json.getBoolean("result")) {
                if (session.getId().equals(json.getJSONObject("data").getString("random"))) {
                    String hash_cv = getHash(realresult, config.autoPassword.encKey);
                    if (hash_cv != null && hash_cv.equals(hash)) {
                        session.setAttribute("logined", user_id)
                    } else {
                        json.put("result", false)
                        json.put("code", "000.2")
                        json.put("msg", "hashError")
                    }
                } else {
                    json.put("result", false)
                    json.put("code", "000.2")
                    json.put("msg", "Forgery")
                }
            }
        }

        render "{\"result\":${json.getBoolean("result")}, \"code\": \"${json.getString("code")}\", \"msg\":\"${json.getString("msg")}\", \"data\":\"\"}"
    }


    def checkUserOTP() {


        def config = grailsApplication.config

        String user_id = params.corp_user_id		//로그인 페이지의 AutoPassword 사용자 아이디 입력 필도
        String user_otp = params.user_otp

        String corp_user_id = getUserInfo(user_id)
        if(corp_user_id == null) {
            render "{\"result\":false, \"code\": \"001.4\", \"msg\":\"미등록\", \"data\":\"\"}"
            return
        }

        String token = getTokenForOneTime()

        String url = "http://localhost:11040/otp/rest/adapter/auth/checkOTP"
        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("token", token)
                .add("corp_user_id", corp_user_id)
                .add("session_yn", "N")
                .add("otp_token", user_otp)
        String result = new HttpConnection().sendPost(url, params)
        JSONObject json = new JSONObject(result)
        System.out.println(json.getBoolean("result"))
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + user_id)

        if (json.getBoolean("result")) {
            session.setAttribute("logined", user_id)
        }
    }

    def checkUserPassword() {

        def config = grailsApplication.config

        String corp_user_id = request.getParameter("user_id")
        String user_password = request.getParameter("user_password")

        /****************************** 구현부분  시작**********************************/
        //사용자 아이디/비밀번호 존재하는지 확인
                boolean isOK = false; 			//정보 변경(존재할 경우 true, 존재하지 않을 경우 false)
                boolean isAutoPW = false; 		//정보 변경(AutoPassword를 사용하는 경우 true, 사용하지 않는 경우 false)

        def user = User.findByUsername(corp_user_id)

        if(user) {
            String encPassword = springSecurityService.encodePassword(user_password)

            if(user.password.equals(encPassword)) {
                isOK = true
            }

            def autoPasswordOID = AutoPasswordOID.findByUser(user);

            if(autoPasswordOID) {
                isAutoPW = true
            }
        }

        //사용자 정보 확인(기존 사용자 Table에 있는 정보)


        if (isOK) {

            if (isAutoPW) {
                render """<script>
                            alert("AutoPassword를 사용하는 사용자입니다.");
                            location.href="${request.contextPath}/user/register";
                            </script>"""
            } else {
                session.setAttribute("DualAuthData.join.corp_user_id", corp_user_id);

                String url = "http://localhost:11040/otp/rest/adapter/user/getUser";
                HttpParameter params = new HttpParameter()
                        .add("corp_id", config.autoPassword.corpId)
                        .add("corp_user_id", corp_user_id)
                        .add("token", config.autoPassword.managerToken);

                String result = new HttpConnection().sendPost(url, params);
                JSONObject json = new JSONObject(result);
                if (json.getBoolean("result")) {
                    response.sendRedirect("../join_3.jsp");

                } else if ("630.3".equals(json.getString("code"))) {
                    response.sendRedirect("../join_1.jsp?user_id="+corp_user_id+"");
                } else {
                    render """<script>
                            alert("에러입니다. 관리자에게 문의 하십시요.(에러코드 : ${json.getString("code")})");
                    location.href = "${request.contextPath}/user/register";
                    </script>"""
                    return
		        }
            }
        } else {
            render """<script>
                            alert("ID/Password가 틀렸습니다.");
                    location.href = "${request.contextPath}/user/register";
                    </script>"""
            return

        }

        /****************************** 구현부분   종료**********************************/
    }

    def corpUserID() {


        String user_pw = params.user_pw		//AutoPassword Server에서 전달되는 본인확인 번호



        /****************************** 기능구현  시작**********************************/
        //사용자 본인확인 번호 확인
        //user_pw 정보(본인확인 번호)를 이용하여 OID 정보 추출 (Temp Table에 있는 정보)

        AutoPasswordOID autoPasswordOID = AutoPasswordOID.findByUserNo(user_pw)

        boolean isID = true;					//정보 변경(존재할 경우 true, 존재하지 않을 경우 false)
        String corp_user_id = "";				//정보 변경(Temp Table에서 본인확인 번호로 사용자 OID 정보 확인)
        String user_id = "";
        if (autoPasswordOID) {
            corp_user_id		= autoPasswordOID.oid
            user_id              = autoPasswordOID.user.username
        } else{
            isID = false;
        }

        /****************************** 기능구현 종료**********************************/

	    if (isID) {

            User user = autoPasswordOID.user

            user.oid = autoPasswordOID.oid
            user.save(flush: true)

            autoPasswordOID.delete(flush: true)

            /****************************** 기능구현 종료**********************************/

            render "{\"result\":true, \"msg\":\"OK\", \"code\":\"000.0\", \"data\":{\"corp_user_id\":\"${corp_user_id}\"}}"
        } else {
            render "{\"result\":false, \"msg\":\"NO USER\", \"code\":\"001.4\"}"
        }
    }

    def  delUserAutopassword() {


        def config = grailsApplication.config

        String user_id = params.corp_user_id	//로그인 페이지의 AutoPassword 사용자 아이디 입력 필드

        User user = User.findByUsername(user_id)


        /****************************** 기능구현 종료**********************************/


        if (user) {
            JSONObject json = null;

            String url = "http://localhost:11040/otp/rest/autopassword/delCorpUserInfo"
            HttpParameter params = new HttpParameter()
                    .add("corp_user_id", user.oid)
                    .add("corp_id", config.autoPassword.corpId)
                    .add("token", config.autoPassword.managerToken)
                    .add("site_user_id", user_id);

            String result = new HttpConnection().sendPost(url, params)
            json = new JSONObject(result)


            if (json.getBoolean("result")) {

                user.oid = null
                user.save(flush: true)

                render """<script>
                        alert("해지 완료");
                        location.href = "${request.contextPath}/user/auth";
                        </script>"""
            } else {
                render """<script>
                    alert("해지 시 오류가 발생하였습니다.");
                    </script>"""
            }

        }
    }

    def joinStep() {
        render view: "joinStep"
    }

    private String getTokenForOneTime() {

        def config = grailsApplication.config

        String url = "http://localhost:11040/otp/rest/token/getTokenForOneTime"
        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("period", 60 + "")
        String result = new HttpConnection().sendPost(url, params)
        JSONObject json = new JSONObject(result)
        if (json.getBoolean("result")) {
            return OTPUtiles.getDecryptAES(json.getJSONObject("data").getString("token"), config.autoPassword.encKey)
        }
        return null
    }

    private String getUserInfo(String user_id) {

        User user = User.findByUsername(user_id)

        return user.oid
    }

    def userOID() {
        def config = grailsApplication.config

        String user_serviceKey = randomService.nextInteger(100000, 999999).toString() // formatter.format(new java.util.Date());			//정보 변경

        JSONObject json = null;
        String token = config.autoPassword.managerToken;
        String corp_user_id = "";
        long period_ms = 60000;
        long period_time = 1;
        String url = "http://localhost:11040/otp/rest/autopassword/getTmpCorpUserID";

        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("token", token);

        String result = new HttpConnection().sendPost(url, params);
        json = new JSONObject(result);
        if (json.getBoolean("result")) {
            JSONObject datajson = json.getJSONObject("data");

            corp_user_id = datajson.get("corp_user_id").toString();
            //AutoPassword 서버에서 발급한 사용자 OID
            period_ms = Integer.parseInt(datajson.get("period_ms").toString());
            //발급한 사용자 OID의 사용 가능 시간(분)  - 사용 가능 시간은 AutoPassword 앱의 등록 시간을 뜻함.
            period_time = (period_ms / 1000) / 60;

            session.setAttribute("corp_user_id", corp_user_id);

            //AutoPassword Server에서 사용자 OID를 받아오면
            //사용자 Temp Table에 저장한다.

            User user = springSecurityService.currentUser

            def autoPasswordOID = new AutoPasswordOID(user: user, oid: corp_user_id, userNo: user_serviceKey)
            autoPasswordOID.save(flush: true)

            render "{\"result\":true, \"code\": \"Z10.1\", \"msg\":\"Check Your ID\", \"user_serviceKey\":\"${user_serviceKey}\", \"corp_user_id\":\"${corp_user_id}\", \"period_ms\":\"${period_ms}\"}"
        } else {
            render "{\"result\":false, \"code\": \"Z10.1\", \"msg\":\"Check Your ID\", \"data\":\"\"}"
        }
    }

    private static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null
        }

        StringBuffer sb = new StringBuffer(ba.length * 2)
        String hexNumber
        for (int x = 0; x < ba.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & ba[x])
            sb.append(hexNumber.substring(hexNumber.length() - 2))
        }

        return sb.toString()
    }

    private String getHash(String data, String enckey) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        try {
            SecretKeySpec signingKey = new SecretKeySpec(enckey.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);	mac.init(signingKey);
            return byteArrayToHex(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

}
