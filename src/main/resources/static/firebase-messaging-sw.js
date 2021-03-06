importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js');

// Initialize Firebase
var config = {
  apiKey: "AIzaSyAKegY8LM_wqmn2twvBsMh0LsvGwsUjS6E",
  authDomain: "gorany-df5bd.firebaseapp.com",
  databaseURL: "https://gorany-df5bd.firebaseio.com",
  projectId: "gorany-df5bd",
  storageBucket: "gorany-df5bd.appspot.com",
  messagingSenderId: "551706831448"
};

firebase.initializeApp(config);

const messaging = firebase.messaging();

messaging.setBackgroundMessageHandler(function(payload){

	const title = "고라니 서비스";
	const options = {
			body: payload.notification.body,
			icon: payload.notification.icon
	}; 	
	
	
	return self.registration.showNotification(title,options);
});


self.addEventListener('install', function(event) {
	  console.log('Service Worker installing.');
	});

	self.addEventListener('activate', function(event) {
	  console.log('Service Worker activating.');  
	});
	
	
	