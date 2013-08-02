<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height"> 
  <title>출근등록시스템</title>
  <style>
    html, body, div, span, applet, object, iframe,
    h1, h2, h3, h4, h5, h6, p, blockquote, pre,
    a, abbr, acronym, address, big, cite, code,
    del, dfn, em, img, ins, kbd, q, s, samp,
    small, strike, strong, sub, sup, tt, var,
    b, u, i, center,
    dl, dt, dd, ol, ul, li,
    fieldset, form, label, legend,
    table, caption, tbody, tfoot, thead, tr, th, td,
    article, aside, canvas, details, embed,
    figure, figcaption, footer, header, hgroup,
    menu, nav, output, ruby, section, summary,
    time, mark, audio, video {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline;
    }
    
    /* HTML5 display-role reset for older browsers */
    article, aside, details, figcaption, figure,
    footer, header, hgroup, menu, nav, section {
        display: block;
    }
    
    ol, ul {
        list-style: none;
    }
    
    blockquote, q {
        quotes: none;
    }
    
    blockquote:before, blockquote:after,
    q:before, q:after {
        content: '';
        content: none;
    }
    
    table {
        border-collapse: collapse;
        border-spacing: 0;
    }
    
    #watch{ font-size:15pt;}
    #attendBtn{ width:90%; bottom:0; margin:0 5%; padding:10px 0;}
    section.page{ position:relative; text-align:center;}
  </style>
</head>
<body>
<section class="page">
  <form id="f" method="post">
  <p id="watch"></p>
  <button id="attendBtn" type="submit">출근체크</button>
  </form>
</section>
<script src="//code.jquery.com/jquery-latest.min.js"></script>
<script>
var watch = (function(){
	var d = new Date()
    , dayOfTheWeek = { 0:'일', 1:'월', 2:'화', 3:'수', 4:'목', 5:'금', 6:'토' }
    , DAYCHANGE = '23:59:59';
	
	function lPad(s){
		return (s.toString().length < 2 && '0') + s;
	}
	
	function getDate(){
        return [d.getFullYear(), '년 '
                , lPad(d.getMonth()), '월 '
                , lPad(d.getDate()), '일 '
                , dayOfTheWeek[d.getDay()], '요일'].join('');
    }
	
	function getTime(){
		return [lPad(d.getHours()), ':'
                , lPad(d.getMinutes()), ':'
                , lPad(d.getSeconds())].join('');
	}
	
	return {
        date: getDate()
        , time: null
        , ticktock: function(){
        	this.time == DAYCHANGE && (this.date = getDate());
            this.time = getTime();
            
            d.setTime(d.getTime() + 1000);
        }
    };
})()
, timeCheck = (function(f){
	return f(), f;
})(function(){
	watch.ticktock();
	
	$('#watch').html(watch.date + '<br/>' + watch.time);
});

window.setInterval(function(){ timeCheck(); }, '1000');

$('#f').submit(function(e){
	$.ajax({
		'url': '',
		'type': 'post',
        'data': null,
        'dataType': 'json',
        'cache': false, //ajaxSetup에서 해주고 있음
        success: function(data){
        	console.log(data);
        }
        , error: function(data, status, xhr){
            //console.log('response : ' + data + '\nstatus : ' + status + '\nxhr : ' + xhr);
        }
	});
	return false;
});
</script>
</body>
</html>