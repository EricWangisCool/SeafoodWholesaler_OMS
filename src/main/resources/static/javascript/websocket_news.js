/**
 * 
 */
function ajaxmethod2(connurl, data) {

    let returnObj = {
        responseObj: "",
        responseText: "",
        responseStatus: "",
        responseToken: ""
    };
    try {
         $.ajax({
            type: 'post',
            url: connurl,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            dataType: 'json',
            crossDomain: true,
            async:!1,
            headers: createAuthorizationTokenHeader(),
            xhrFields: {
                withCredentials: true
            },
            complete: function (XMLHttpRequest, textStatus) {
                returnObj.responseText = XMLHttpRequest.responseText;
                returnObj.responseObj = XMLHttpRequest.responseJSON;
                returnObj.responseStatus = XMLHttpRequest.status;
                returnObj.responseToken = XMLHttpRequest.getResponseHeader("authorization");
            },
           
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                
            }
        });
    } catch (err) {
        console.log(err);
    }
    return returnObj;
}
function commoncontextpath2() {
    let connurl = "../common/getContextPath";
    return  ajaxmethod2(connurl, "");
}
function asyncGetCTX() {
            returnObj2 =  commoncontextpath2();
            ctx = returnObj2.responseText;
            
            return ctx
            }
var ctx_tmp = asyncGetCTX();
ctx_path = ctx_tmp === 'nocontext' ? "" : ctx_tmp
var protocol = window.location.protocol;
    newsCounter = 0;
	if(protocol == "https:"){
		sock = new SockJS("https://"+window.location.host  + ctx_path +"/ws");
      }else{
    	sock = new SockJS("http://"+window.location.host+ ctx_path  +"/ws");
      }
	let client = Stomp.over(sock);
	client.debug = null
	var token = getJwtToken();
	var header = {
			jwtToken:token
	}
	// 溝通訂閱代理位置
	client.connect(header, frame => {
    // /PersonalNotify  WebSocket 訊息來源
    // /user 為規定必加
    client.subscribe("/user/PersonalNotify/Notify", payload => {
    	
        console.log( JSON.parse(payload.body) );
        var data = JSON.parse(payload.body)
        console.log(data)
        console.log(data.titleType)
        let singleHtmlTr = `
                <tr data-value="">
                    <td data-label="icon"><i class="fas fa-comment-plus fs-5"></i></td>
                    <td data-label="內容">
                        <q>${data.titleType}</q><span class="status-indicator"></span>
                        <span class="d-block py-3">${data.message}</span>
                        <span class="d-block">${data.messageTime}</span>
                    </td>
                </tr>`;

$(".newstable").append(singleHtmlTr);
    	});

	});
	
	
	// 預留方法 未使用
	function sendMessage(){
		testbean = {
				titleType : "測試",
				message : "測試訊息",
				messageTime : new Date()
		}
	    console.log("send!")
	    client.send('/PersonalNotify/Send', {},JSON.stringify(testbean));

	}
	
	//消除消息
	$(".newsli").click(function(){
		$(".rounded-pill").hide();
		newsCounter = 0;
	});