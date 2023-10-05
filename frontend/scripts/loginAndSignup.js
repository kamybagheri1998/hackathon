document.getElementById("signInForm").addEventListener("submit", function (e) {
  if (!document.getElementById("signInForm").checkValidity()) {
    e.preventDefault()
    e.stopPropagation()
    return;
  }

  document.getElementById("signInForm").classList.add('was-validated');

  e.preventDefault();

  const email = document.getElementById("signInEmail").value;
  const password = document.getElementById("signInPassword").value;

  // TODO add post api url
  // TODO see order placed html for div with invalid email etc. -> turn visible on bad input
  fetch('http://localhost:5432/user?' + new URLSearchParams({
    email: email,
    password: password
  }), {method: 'GET'})
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      // TODO add login token
      window.location.replace("schoolPortal.html");
    } else {
      document.getElementById("failToastSignIn").classList.remove("d-none")
    }
  })
  .catch(error => {
    console.error('Error:', error);
    window.location.replace("error.html");
  });
});

document.getElementById("signUpForm").addEventListener("submit", function (e) {
  if (!document.getElementById("signUpForm").checkValidity()) {
    e.preventDefault()
    e.stopPropagation()
    return;
  }

  document.getElementById("signUpForm").classList.add('was-validated');

  e.preventDefault();

  const email = document.getElementById("signUpEmail").value;
  const institute = document.getElementById("signUpInstitute").value;
  const password = document.getElementById("signUpPassword").value;

  fetch('http://localhost:8080/user?' + new URLSearchParams({
    email: email,
    institute: institute,
    password: password
  }), {method: 'POST'})
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      document.getElementById("successToast").classList.remove("d-none")
      // TODO add login token
      // TODO add link back to home
    } else {
      alert(data)
      document.getElementById("failToastSignUp").classList.remove("d-none")
    }
  })
  .catch(error => {
    alert(error)
    console.error('Error:', error);
    document.getElementById("failToastSignUp").classList.remove("d-none");
    //window.location.replace("error.html");
  });
});
