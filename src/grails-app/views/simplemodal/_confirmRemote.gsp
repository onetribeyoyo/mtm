<div class="simplemodal-content">${params.confirm_message}</div>

<div class="buttonset">
  <g:remoteLink controller="${params.confirm_controller}" action="${params.confirm_action}" id="${params.confirm_id}"
        class="button ${params.confirm_confirmClass}"

        after="${params.confirm_after}"
        asynchronous="${params.confirm_asynchronous}"
        before="${params.confirm_before}"
        update="${params.confirm_update}"

        onSuccess="${params.confirm_onSuccess}"
        onFailure="${params.confirm_onFailure}"
        on_ERROR_CODE="${params.confirm_on_ERROR_CODE}"
        onUninitialized="${params.confirm_onUninitialized}"
        onLoading="${params.confirm_onLoading}"
        onLoaded="${params.confirm_onLoaded}"
        onComplete="${params.confirm_onComplete}"

    >${params.confirm_confirmLabel}</g:remoteLink>
  <simplemodal:closeButton>${params.confirm_cancelLabel}</simplemodal:closeButton>
</div>
