<script id="template-confirm-modal" type="text/html">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3>{{title}}</h3>
            </div>
            <div class="modal-body">
                {{{body}}}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="{{id-boton-confirm}}" >Si</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</script>