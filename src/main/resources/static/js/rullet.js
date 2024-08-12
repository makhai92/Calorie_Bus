/*var rolLength = 6; // 해당 룰렛 콘텐츠 갯수
var setNum; // 랜덤숫자 담을 변수
var hiddenInput = document.createElement("input");
hiddenInput.className = "hidden-input";

//랜덤숫자부여
const rRandom = () => {
  var min = Math.ceil(0);
  var max = Math.floor(rolLength - 1);
  return Math.floor(Math.random() * (max - min)) + min;
};

const rRotate = () => {
  var panel = document.querySelector(".rouletter-wacu");
  var btn = document.querySelector(".rouletter-btn");
  var deg = [];
  // 룰렛 각도 설정(rolLength = 6)
  for (var i = 1, len = rolLength; i <= len; i++) {
    deg.push((360 / len) * i);
  }
  
  // 랜덤 생성된 숫자를 히든 인풋에 넣기
  var num = 0;
  document.body.append(hiddenInput);
  setNum = hiddenInput.value = rRandom();
	
  // 애니설정
  var ani = setInterval(() => {
    num++;
    panel.style.transform = "rotate(" + 360 * num + "deg)";
    btn.disabled = true; //button,input
    btn.style.pointerEvents = "none"; //a 태그
    
    // 총 50에 다달했을때, 즉 마지막 바퀴를 돌고나서
    if (num === 50) {
      clearInterval(ani);
      panel.style.transform = `rotate(${deg[setNum]}deg)`;
    }
  }, 50);
};

// 정해진 alert띄우기, custom modal등
const rLayerPopup = (num) => {
  switch (num) {
    case 1:
      alert("당첨!! 스타벅스 아메리카노");
      break;
    case 3:
      alert("당첨!! 햄버거 세트 교환권");
      break;
    case 5:
      alert("당첨!! CU 3,000원 상품권");
      break;
    default:
      alert("꽝! 다음기회에");
  }
};

// reset
const rReset = (ele) => {
  setTimeout(() => {
    ele.disabled = false;
    ele.style.pointerEvents = "auto";
    rLayerPopup(setNum);
    hiddenInput.remove();
  }, 5500);
};

// 룰렛 이벤트 클릭 버튼
document.addEventListener("click", function (e) {
  var target = e.target;
  if (target.tagName === "BUTTON") {
    rRotate();
    rReset(target);
  }
});

// roulette default
document.getElementById("app").innerHTML = `
<div class="rouletter">
    <div class="rouletter-bg">
        <div class="rouletter-wacu"></div>
    </div>
    <div class="rouletter-arrow"></div>
    <button class="rouletter-btn">start</button>
</div>
`;
*/












var rolLength = 6; // 해당 룰렛 콘텐츠 갯수
var setNum; // 랜덤숫자 담을 변수
var hiddenInput = document.createElement("input");
hiddenInput.className = "hidden-input";
var isSpun = false; // 룰렛이 이미 돌아갔는지 확인하는 변수

//랜덤숫자부여
const rRandom = () => {
  var min = Math.ceil(0);
  var max = Math.floor(rolLength - 1);
  return Math.floor(Math.random() * (max - min)) + min;
};

const rRotate = () => {
  var panel = document.querySelector(".rouletter-wacu");
  var btn = document.querySelector(".rouletter-btn");
  var deg = [];
  // 룰렛 각도 설정(rolLength = 6)
  for (var i = 1, len = rolLength; i <= len; i++) {
    deg.push((360 / len) * i);
  }
  
  // 랜덤 생성된 숫자를 히든 인풋에 넣기
  var num = 0;
  document.body.append(hiddenInput);
  setNum = hiddenInput.value = rRandom();
  
  // 애니설정
  var ani = setInterval(() => {
    num++;
    panel.style.transform = "rotate(" + 360 * num + "deg)";
    btn.disabled = true; //button,input
    btn.style.pointerEvents = "none"; //a 태그
    
    // 총 50에 다달했을때, 즉 마지막 바퀴를 돌고나서
    if (num === 50) {
      clearInterval(ani);
      panel.style.transform = `rotate(${deg[setNum]}deg)`;
    }
  }, 50);
};

// 정해진 alert띄우기, custom modal등
const rLayerPopup = (num) => {
  let eventItemName = ""; 
  switch (num) {
    case 1:
      alert("당첨!! 스타벅스 아메리카노");
      eventItemName = "스타벅스 아메리카노";
      break;
    case 3:
      alert("당첨!! 햄버거 세트 교환권");
      eventItemName = "햄버거 세트 교환권";
      break;
    case 5:
      alert("당첨!! CU 3,000원 상품권");
      eventItemName = "CU 3000원 상품권";
      break;
    default:
      alert("꽝! 다음기회에");
      eventItemName = null;
  }
  if(eventItemName != null){
  	$.ajax({
  		url : "/rulletPage/insertEventItem",
  		data : {eventItemName : eventItemName},
  		type : "post",
  		success : function(data){
  			if(data == -1){
  				alert("에러, 당첨 정보를 캡쳐해 고객센터에 문의하세요.");
  			}else{
  				alert("상품은 관리자를 통해 지급됩니다.");
  			}
  		},
  		error : function(){
  			alert("에러, 당첨 정보를 캡쳐해 고객센터에 문의하세요.");
  		}
  	})
  }
};

// reset
const rReset = (ele) => {
  setTimeout(() => {
    rLayerPopup(setNum);
    hiddenInput.remove();
    // 룰렛이 한 번만 돌아가도록 설정
    isSpun = true;
  }, 5500);
};

// 룰렛 이벤트 클릭 버튼
document.addEventListener("click", function (e) {
  var target = e.target;
  if (target.tagName === "BUTTON") {
  	$.ajax({
  		url : "/rulletPage/getRulletCount",
  		type : "post",
  		success : function(data){
  			 if (data > 0) {
			      rRotate();
			      rReset(target);
			 }else if(data == -10){
			 	alert("로그인해");
			 }else if(data == -1){
			 	alert("서버 오류입니다. 잠시후 다시 이용해주세요.");
			 }else{
			      alert("룰렛 이벤트 참여 기회가 없습니다.");
			 }
  		},
  		error : function(){
  			console.log("error");
		}  		
  	});
  }
});

// roulette default
document.getElementById("app").innerHTML = `
<div class="rouletter">
    <div class="rouletter-bg">
        <div class="rouletter-wacu"></div>
    </div>
    <div class="rouletter-arrow"></div>
    <div class="rouletter-btn"></button>
</div>
`;
