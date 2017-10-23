package net.okjsp

import com.estorm.framework.util.HttpConnection
import com.estorm.framework.util.HttpParameter
import com.estorm.framework.util.OTPUtiles
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional(readOnly = true)
class AutoPasswordController {

    def grailsApplication

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

        /****************************** 기능구현  시작**********************************/
        //사용자 아이디가 존재하는지 확인
        //사용자 OID 정보 확인(기존 사용자 Table에 있는 정보)
        String ServiceUrl = config.grails.serverURL				            //Service Site의 Domain으로 변경
        String service_url = ServiceUrl + "/autoPassword/userInfo"			//Service Site의 처리 URL으로 변경
        HttpParameter service_params = new HttpParameter()
                .add("user_id", params.user_id)

        String service_result = new HttpConnection().sendPost(service_url, service_params)
        JSONObject service_json = new JSONObject(service_result)

        boolean isID = true										//정보 변경(존재할 경우 true, 존재하지 않을 경우 false)
        String corpUserId = ""								    //정보 변경(사용자의 OID 정보)
        if (service_json.getBoolean("result")) {
            isID = true
            corpUserId = service_json.get("userOID").toString()
        }else{
            render "{\"result\":false, \"code\": \"001.4\", \"msg\":\"미등록\", \"data\":\"\"}"
            return
        }

        String token = ""

        String url = "http://localhost:11040/otp/rest/token/getTokenForOneTime"
        HttpParameter params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("period", (60) + "")

        String result = new HttpConnection().sendPost(url, params)
        JSONObject json = new JSONObject(result)

        if (json.getBoolean("result")) {
            token = OTPUtiles.getDecryptAES(json.getJSONObject("data").getString("token"), config.autoPassword.encKey)
        }


        url = "http://localhost:11040/otp/rest/adapter/auth/cancelROTP"

        params = new HttpParameter()
                .add("corp_id", config.autoPassword.corpId)
                .add("token", token)
                .add("corp_user_id", corpUserId)
                .add("session_id", session.getId())

        result = new HttpConnection().sendPost(url, params)

        render "{\"result\":true, \"code\": \"000.0\", \"msg\":\"성공\", \"data\":\"\"}"
    }

    def checkID() {

    }

    def joinStep() {
        render view: "joinStep"
    }
}
