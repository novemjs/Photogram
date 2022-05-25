// (1) 회원정보 수정
function update(userid,event) {
	event.preventDefault();	//폼태그 액션을 막아준다.
	let data=$("#profileUpdate").serialize();
	
	console.log(data);
	
	$.ajax({
		type:"put",
		url:`/api/user/${userid}`,
		data:data,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		dataType:"json"
	}).done(res=>{	//HttpStatus 상태코드 200번대
		console.log("update 성공",res);
		location.href=`/user/${userid}`;
	}).fail(error=>{	//HttpStatus 상태코드 200번대가 아닐 때
		if(error.data==null){
			alert(error.responseJSON.message);
		}else{
			alert(JSON.stringify(error.responseJSON.data));
			console.log("update 실패",error);
		}
		
	});
}