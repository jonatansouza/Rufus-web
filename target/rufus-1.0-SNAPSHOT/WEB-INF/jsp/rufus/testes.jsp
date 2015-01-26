<form id="myform" action="http://auth.comcidis.lncc.br:3000/oauth/authorize" method="post">
    <input value="code" name="response_type">
    <input value="96faaa00d5da1e1117cce031dcf1083356fcc6a3d63b0ca5c29bec7627a00a38" name="client_id">
    <input value="23773eb69593d259ddb981311fa55f2e921b88c49ba156b75491004e6ee028ba" name="client_secret">
    <input value="http://192.168.1.113:8084/rufus/dashboard" name="redirect_uri">
    <input type="submit">   
</form>

<div class="container">
        <h3>Animated button</h3>
        <button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Loading...</button>
    </div>  

<form id="myform" action="http://auth.comcidis.lncc.br:3000/users/sign_in" method="post">
    <input name="user[email]">
    <input type="password" name="user[password]">
    <input type="submit">   
</form>
