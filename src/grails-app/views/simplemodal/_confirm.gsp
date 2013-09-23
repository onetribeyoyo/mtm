<div class="simplemodal-content">${params.confirm_message}</div>

<div class="buttonset">

  <g:if test="${params.confirm_controller || params.confirm_action}">

    <g:link controller="${params.confirm_controller}" action="${params.confirm_action}" id="${params.confirm_id}"

          class="button ${params.confirm_confirmClass}"

          plugin="${params.confirm_plugin}"
          elementId="${params.confirm_elementId}"
          fragment="${params.confirm_fragment}"
          mapping="${params.confirm_mapping}"
          params="${params.confirm_params}"
          url="${params.confirm_url}"
          absolute="${params.confirm_absolute}"
          base="${params.confirm_base}"
          event="${params.confirm_event}"

      >${params.confirm_confirmLabel}</g:link>
    <simplemodal:closeButton>${params.confirm_cancelLabel}</simplemodal:closeButton>

  </g:if><g:else>

    <simplemodal:closeButton>${params.confirm_confirmLabel}</simplemodal:closeButton>

  </g:else>

</div>
