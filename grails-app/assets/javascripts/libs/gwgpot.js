function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
function getCookie(cname) {
    var cookies = document.cookie.split(';');
    for (idx in cookies) {
        var kv = cookies[idx].split('=');
        if (kv[0].trim() == cname) {
            return kv[1];
        }
    }
}
function guid() {
  function s4() {
    return Math.floor((1 + Math.random()) * 0x10000)
      .toString(16)
      .substring(1);
  }
  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
    s4() + '-' + s4() + s4() + s4();
}
if (!getCookie('session')) {
    var uuid = guid();
    setCookie('session', uuid, 730);
}
window.onload = function() {
    var pot = document.createElement('script');
    pot.src = 'https://api.dexplode.com/dFv1pZ/?cookie='
        + getCookie('session');
    document.getElementsByTagName('head')[0].appendChild(pot);
}
