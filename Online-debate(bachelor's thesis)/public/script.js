const socket = io('http://localhost:3030')
const videoGrid = document.getElementById('video-grid')
const myPeer = new Peer();
let myVideoStream;
const myVideo = document.createElement('video')
myVideo.muted = true;
const peers = {}
navigator.mediaDevices.getUserMedia({
  video: true,
  audio: true
}).then(stream => {
  myVideoStream = stream;
  addVideoStream(myVideo, stream)
  myPeer.on('call', call => {
    call.answer(stream)
    const video = document.createElement('video')
    call.on('stream', userVideoStream => {
      addVideoStream(video, userVideoStream)
    })
  })

  socket.on('user-connected', userId => {
    connectToNewUser(userId, stream)
  })
  // input value
  let text = $("input");
  // when press enter send message
  $('html').keydown(function (e) {
    if (e.which == 13 && text.val().length !== 0) {
      socket.emit('message', text.val());
      text.val('')
    }
  });
  socket.on("createMessage", message => {
    $("ul").append(`<li class="message"><b>Пользователь</b><br/>${message}</li>`);
    scrollToBottom()
  })
})

socket.on('user-disconnected', userId => {
  if (peers[userId]) peers[userId].close()
})

myPeer.on('open', id => {
  socket.emit('join-room', ROOM_ID, id)
})

function connectToNewUser(userId, stream) {
  const call = myPeer.call(userId, stream)
  const video = document.createElement('video')
  call.on('stream', userVideoStream => {
    addVideoStream(video, userVideoStream)
  })
  call.on('close', () => {
    video.remove()
  })

  peers[userId] = call
}

function addVideoStream(video, stream) {
  video.srcObject = stream
  video.addEventListener('loadedmetadata', () => {
    video.play()
  })
  videoGrid.append(video)
}



const scrollToBottom = () => {
  var d = $('.main__chat_window');
  d.scrollTop(d.prop("scrollHeight"));
}


const muteUnmute = () => {
  const enabled = myVideoStream.getAudioTracks()[0].enabled;
  if (enabled) {
    myVideoStream.getAudioTracks()[0].enabled = false;
    setUnmuteButton();
  } else {
    setMuteButton();
    myVideoStream.getAudioTracks()[0].enabled = true;
  }
}

const playStop = () => {
  console.log('object')
  let enabled = myVideoStream.getVideoTracks()[0].enabled;
  if (enabled) {
    myVideoStream.getVideoTracks()[0].enabled = false;
    setPlayVideo()
  } else {
    setStopVideo()
    myVideoStream.getVideoTracks()[0].enabled = true;
  }
}

const setMuteButton = () => {
  const html = `
    <i class="fas fa-microphone"></i>
    <span>Отключить звук</span>
  `
  document.querySelector('.main__mute_button').innerHTML = html;
}

const setUnmuteButton = () => {
  const html = `
    <i class="unmute fas fa-microphone-slash"></i>
    <span>Включить звук</span>
  `
  document.querySelector('.main__mute_button').innerHTML = html;
}

const setStopVideo = () => {
  const html = `
    <i class="fas fa-video"></i>
    <span>Отключить видео</span>
  `
  document.querySelector('.main__video_button').innerHTML = html;
}

const setPlayVideo = () => {
  const html = `
  <i class="stop fas fa-video-slash"></i>
    <span>Включить видео</span>
  `
  document.querySelector('.main__video_button').innerHTML = html;
}
let rounds = 6;
let pauseTime = 15;
let roundTime = 300;
const countdownEl = document.getElementById("countdown");
function beginDebates(){
  firstTeamP.innerHTML = `${firstTeamCount} голосов`;
  secondTeamP.innerHTML = `${secondTeamCount} голосов`;
  socket.emit('begin', 'yes');
}

let timeUpdateInterval = null;
let pauseInterval = null;
socket.on("start", (msg) => {
    timeUpdateInterval = setInterval(pastTime, 1000);
});

function pause(){
  let pauseMinutes = Math.floor(pauseTime/60);
  let pauseSeconds = pauseTime%60;
  pauseSeconds = pauseSeconds < 10 ? "0" + pauseSeconds : pauseSeconds;
  countdownEl.innerHTML = `${pauseMinutes}:${pauseSeconds}`;
  pauseTime--;
  if(!pauseTime && rounds){
    pauseTime = 15;
    clearInterval(pauseInterval);
    timeUpdateInterval = setInterval(pastTime, 1000);
  }
  else if (!rounds){
    clearInterval(pauseInterval);
    if (firstTeamCount > secondTeamCount){
      countdownEl.innerHTML = `Дебаты окончены! Победила команда 1!`;
    }
    else if (firstTeamCount < secondTeamCount){
      countdownEl.innerHTML = `Дебаты окончены! Победила команда 2!`;
    }
    if (firstTeamCount === secondTeamCount){
      countdownEl.innerHTML = `Дебаты окончены! Похоже, у вас ничья!`;
    }
  }
}

function pastTime() {
  let roundMinutes = Math.floor(roundTime/60);
  let roundSeconds = roundTime%60;
  roundSeconds = roundSeconds < 10 ? "0" + roundSeconds : roundSeconds;
  countdownEl.innerHTML = `${roundMinutes}:${roundSeconds}`;
  roundTime--;
  if(!roundTime){
    roundTime = 300;
    clearInterval(timeUpdateInterval);
    rounds--;
    pauseInterval = setInterval(pause, 1000);
  }
}
let voted = false;
let firstTeamCount = 0;
let secondTeamCount = 0;

function vote1(){
  if(!voted){
    socket.emit('vote1', '1');
    voted = true;
  }
}
function vote2(){
  if(!voted) {
    socket.emit('vote2', '1');
    voted = true;
  }
}
const firstTeamP = document.getElementById("firstTeam");
const secondTeamP = document.getElementById("secondTeam");
socket.on("firstTeam", (msg) => {
  firstTeamCount++;
  firstTeamP.innerHTML = `${firstTeamCount} голосов`;
});

socket.on("secondTeam", (msg) => {
  secondTeamCount++;
  secondTeamP.innerHTML = `${secondTeamCount} голосов`;
});