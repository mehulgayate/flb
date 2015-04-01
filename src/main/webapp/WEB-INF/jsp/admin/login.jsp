<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>User Login</title>
  <link rel="stylesheet" href="/static/style2.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  
  <script type="text/javascript" src="/static/js/jquery-2.1.0.min.js"></script>
  
</head>

<body>
  <div class="container">
    <section class="register">
      <h1>Admin Login</h1>
      <form method="post" action="/admin/authenticate" id="loginForm">
      <div class="reg_section personal_info">
      <h3>Login</h3>
      <input id="email" type="text" name="email" value="" placeholder="Your E-mail Address" required>
      <input id="password" type="password" name="password" value="" placeholder="Your Password" required>
      <p>${error}</p>
      </div>      
      <p class="terms">
        <label>
          
        </label>
      </p>      
      <p class="submit"><input type="submit" name="commit" value="Login"></p>
      </form>
    </section>
  </div>
</body>
</html>