document.getElementById("signInForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const email = document.getElementById("signInEmail").value;
  const password = document.getElementById("signInPassword").value;

  const credentials = {
    username: email,
    password: password
  };
  // TODO add post api url
  // TODO see order placed html for div with invalid email etc. -> turn visible on bad input
  fetch('', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  })
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      // TODO maybe a modal?
      // TODO add login token
      // TODO add correct url
      window.location.replace("http://localhost:8080/");
    } else {
      window.location.replace("https://www.google.com");
    }
  })
  .catch(error => {
    console.error('Error:', error);
    window.location.replace("error.html");
  });
});
