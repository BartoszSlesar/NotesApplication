import { unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin/register-styles';

import vaadinButtonCss from 'themes/personal-note-application/components/vaadin-button.css?inline';


if (!document['_vaadintheme_personal-note-application_componentCss']) {
  registerStyles(
        'vaadin-button',
        unsafeCSS(vaadinButtonCss.toString())
      );
      
  document['_vaadintheme_personal-note-application_componentCss'] = true;
}

if (import.meta.hot) {
  import.meta.hot.accept((module) => {
    window.location.reload();
  });
}

