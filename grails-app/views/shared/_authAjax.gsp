<div class="modal fade" id="auth-form">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><g:message code="springSecurity.login.header"/></h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger" id="id-login-error"></div>
                <form role="form" action='${request.contextPath}/j_spring_security_check' method='POST'
                      id='ajaxLoginForm' name='ajaxLoginForm' autocomplete='off' class="form-signin">
                        <input type="email" class="form-control" placeholder="Enter user" name='j_username' required="" autofocus="" />
                        <input type="password" class="form-control" placeholder="Password" name="j_password" required="" />
                        <label class="checkbox">
                            <input type="checkbox" name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>> <g:message code="springSecurity.login.remember.me.label"/>
                         </label>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" id="id-login-button" data-url-login="${request.contextPath}/j_spring_security_check"
                        data-url-redirect="${createLink(controller: 'panel', action: 'dashboard')}">${message(code: "springSecurity.login.button")}</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->