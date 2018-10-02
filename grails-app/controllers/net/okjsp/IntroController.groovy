package net.okjsp

import grails.transaction.Transactional

@Transactional(readOnly = true)
class IntroController {

    def ad() {

        int count = 64323

        String countString = count > 1000 ? (Math.ceil(count / 1000) * 1000 as int) : count
        String resultString = ""

        String[] han1 = ["","1","2","3","4","5","6","7","8","9"]
        String[] han2 = ["","십","백","천"]
        String[] han3 = ["","만","억","조","경"]

        int len = countString.length()
        for(int i=len-1; i>=0; i--) {
            String s = countString.substring(len-i-1, len-i)
            resultString += han1[Integer.parseInt(countString.substring(len-i-1, len-i))]
            if (Integer.parseInt(countString.substring(len-i-1, len-i)) > 0)
                resultString += han2[i%4] + (han2[i%4] != "" ? " " : "")
            if (i%4 == 0)
                resultString += han3[(i/4) as int] + (han3[(i/4) as int] != "" ? " " : "")
        }

        render view: "ad", model : [resultString : resultString]
    }
}
