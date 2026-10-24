# TinyPrice: cierre y checkpoints

## Resultado final del paso 16

Cierre documental realizado el 24 de octubre de 2026. TinyPrice funciona con el backend remoto en el TECNO LI7: compilación correcta, cinco pruebas unitarias y dos instrumentadas aprobadas, y ocho pruebas manuales confirmadas por el usuario. La implementación funcional del laboratorio está terminada; las diferencias y actividades sin evidencia se conservan abajo. No se afirma que todos los subpasos opcionales, el registro por referido o la preparación para producción estén completos.

Este cierre leyó únicamente el paso 16 del HTML y utilizó las evidencias existentes. Solo se modificó CHECKPOINTS.md; no se repitieron compilaciones, pruebas, búsquedas, despliegues ni instalaciones, y no se publicaron artefactos ni se hicieron commits o push. No se accionó el botón de progreso del navegador ni se afirma que su indicador esté en 100%.

## Arquitectura y configuración comprobadas

Android (Compose → eventos → ViewModel/StateFlow → DealsRepository) envía POST mediante Retrofit a Supabase search-products. La Edge Function valida la entrada, lee TINYFISH_API_KEY desde Deno.env, consulta TinyFish Search y transforma los resultados a offers. La respuesta vuelve por el repositorio al estado y la interfaz. sourceUrl permite verificar cada resultado. El repositorio demo permanece disponible como alternativa.

- Paquete, namespace, applicationId y launcher: com.apps.iatinyfish / com.apps.iatinyfish.MainActivity.
- Proyecto Supabase: tinyprice-codelab, referencia mogxyjcceqtiitfwoqbr.
- Propiedades persistentes del proyecto: useRemoteDeals=true; edgeRuntimeBaseUrl=https://mogxyjcceqtiitfwoqbr.supabase.co/functions/v1.
- BuildConfig remoto y URL correcta comprobados en las intervenciones anteriores. Las propiedades también se aplican a las compilaciones de Run.
- Edge Function desplegada y verificada ACTIVE, versión 1, con verify_jwt=false. No se modificó durante el cierre.
- La API key no se obtuvo ni se copió a Android. Las inspecciones del APK (10 DEX y recursos de texto) no encontraron TINYFISH_API_KEY, X-API-Key ni api.search.tinyfish.ai. No se comparó contra el valor secreto real ni se presenta este examen como una auditoría exhaustiva del binario.

## Evidencia automatizada y operativa existente

- :app:testDebugUnitTest: cinco pruebas aprobadas (cuatro del ViewModel y una de la plantilla), cero fallos, errores u omitidas.
- :app:connectedDebugAndroidTest: dos pruebas aprobadas en TECNO LI7 con Android 15 (useAppContext y startScreenShowsSearchAndSuggestion), cero fallos, errores u omitidas.
- :app:assembleDebug con propiedades remotas: BUILD SUCCESSFUL. Informes en app/build/test-results/testDebugUnitTest/ y app/build/outputs/androidTest-results/connected/debug/.
- POST real de Nintendo Switch OLED al backend: HTTP 200, nueve ofertas, 1803 ms, request ID 01a16f63-1e06-7d93-a3a9-239426214a19. Evidencia operativa sin cuerpos/credenciales en supabase/step13-verification.json.
- Entradas inválidas: HTTP 400; GET: HTTP 405. No se observó HTTP 429 tras 40 consultas inválidas adicionales, sin llamadas adicionales a TinyFish.
- APK remoto instalado e iniciado por ADB: Status: ok, TinyPrice activa y enfocada; hash instalado igual al APK local. En esa verificación, SHA-256 E423FF70AB469DC9A0A7D80B9CE8B57C76759795094E23A9197DE90A102641B1. Es evidencia de ese artefacto, no una nueva medición del APK después del paso 15.
- El usuario confirmó búsquedas reales desde Android y la revisión de Invocations y Logs de Supabase. Esa revisión del Dashboard se atribuye al usuario; el agente no tuvo acceso visual al Dashboard.

## Ocho pruebas manuales del paso 15

El usuario confirmó expresamente que las ocho pruebas funcionaron en el TECNO LI7. Se registran como verificadas manualmente por el usuario, no como observaciones visuales del agente.

| Prueba | Resultado y responsable |
| --- | --- |
| 1. Buscar sin texto muestra validación | Aprobada manualmente por el usuario |
| 2. Consulta válida muestra carga y resultados | Aprobada manualmente por el usuario |
| 3. Tarjeta abre el detalle correcto | Aprobada manualmente por el usuario |
| 4. Atrás vuelve sin cerrar la app | Aprobada manualmente por el usuario |
| 5. Fuente web abre fuera de la app | Aprobada manualmente por el usuario |
| 6. Claro y oscuro mantienen contraste | Aprobada manualmente por el usuario |
| 7. Rotación no rompe la composición | Aprobada manualmente por el usuario |
| 8. Sin Internet muestra error y permite reintentar | Aprobada manualmente por el usuario |

Checkpoint de calidad del paso 15 satisfecho con la combinación de resultados automatizados existentes y confirmación manual del usuario. La matriz de diagnóstico se revisó sin provocar errores ni consumir búsquedas adicionales.

## Resumen de los 16 pasos

| Paso | Resultado comprobado o límite de evidencia |
| --- | --- |
| 1. Antes de comenzar | Introducción y requisitos leídos en el bloque 1; proyecto existente reutilizado. No se interpreta la lectura como evidencia de registros externos. |
| 2. Arquitectura y recorrido | Separación Android → Supabase → TinyFish implementada y flujo real confirmado por el usuario. |
| 3. Cuenta TinyFish, créditos y clave | Secreto alojado y búsquedas reales prueban una credencial operativa. Sin evidencia específica del registro por referido, créditos o almacenamiento en gestor; no se declaran completados. |
| 4. TinyFish Search | Integración con resultados web reales comprobada a través de Supabase. Prueba directa privada de curl del paso 4 no documentada; no se repite. |
| 5. Entorno Android y skill | Java 25, Gradle 9.6.0, SDK 37, Platform-Tools y Android CLI 1.0.16625370 verificados. Skill y referencias presentes; TECNO conectado y utilizado. Gemini y skills-lock.json pendientes. |
| 6. Proyecto y dependencias | Paquete com.apps.iatinyfish, minSdk 23, Internet, dependencias y BuildConfig configurados; compilación correcta. Alias JUnit corregido con autorización. Revisión visual de referencias del IDE no documentada. |
| 7. Modelo y repositorios | Modelo, demo, DTO, Retrofit, factoría y repositorio remoto copiados del paso; compileDebugKotlin aprobado. |
| 8. Estado y ViewModel | Código del paso, StateFlow, eventos, corrutinas y MainActivity implementados. Cuatro pruebas del ViewModel aprobadas. |
| 9. Compose y tema | Interfaz y tema completos obtenidos de la referencia oficial; compilación aprobada. Detalle, navegación y temas confirmados manualmente por el usuario. TalkBack y escalado de fuente no documentados. |
| 10. Cuenta Supabase | Cuenta existente con GitHub reutilizada, según datos confirmados por el usuario; difiere del registro por correo propuesto en la guía. No se creó otra cuenta. |
| 11. Proyecto y enlace | tinyprice-codelab comprobado ACTIVE_HEALTHY y linked=true; CLI 2.122.0 instalada localmente; configuración de función con verify_jwt=false. |
| 12. Edge Function | index.ts idéntico al paso, un único Deno.serve y clave leída desde Deno.env. Once comprobaciones locales simuladas aprobadas, sin red. Validación nativa Deno no realizada. |
| 13. Secreto, despliegue y backend | Secreto confirmado por nombre, despliegue autorizado, HTTP 200 con nueve ofertas, 400 y 405 observados. Logs/Invocations confirmados por el usuario. 429 no reproducido; rotación de clave sin evidencia. |
| 14. Integración remota | Propiedades remotas persistidas, APK remoto compilado, instalado y abierto por ADB. Búsquedas reales y recorrido confirmados por el usuario. Sin claves en el cliente según las inspecciones documentadas. |
| 15. Calidad | Cinco pruebas unitarias, dos instrumentadas y compilación remota aprobadas; ocho pruebas manuales confirmadas por el usuario; seguridad y matriz revisadas. |
| 16. Cierre | Arquitectura, requisitos finales, evidencias y diferencias revisados; publicación opcional documentada sin ejecutarla. Cierre funcional y documental, sin afirmar preparación para producción. |

## Diferencias resueltas y pendientes conservados

- Supabase se autenticó con la cuenta GitHub existente; no se siguió el alta por correo del paso 10. Diferencia documentada, sin impacto observado en el flujo.
- Gemini CLI es opcional para compilar Android. Su descubrimiento/activación de la skill mediante /trust, /skills reload y /skills list no se verificó.
- skills-lock.json sigue ausente. Android CLI rechazó el comando sin nombre de la guía; la instalación por nombre android-cli terminó correctamente, conservando SKILL.md y referencias, pero no generó el lock. No se fabricó ese archivo.
- JUnit: se conservó 4.13.2 y se restauró únicamente el alias omitido por la guía, con autorización del usuario. Iconos adaptativos trasladados a mipmap-anydpi-v26 para minSdk 23.
- Los fragmentos incompletos de Compose y del tema se completaron con DealsApp.kt, Color.kt y Theme.kt oficiales. Carga y vacío/error están dentro de SearchResults en la referencia, sin funciones independientes LoadingState/EmptyState. No se rediseñó la interfaz.
- El código literal de la función devuelve texto ok para OPTIONS, aunque el checkpoint describe respuestas JSON; se conservó el original. Las otras respuestas comprobadas siguen el contrato JSON.
- HTTP 429 no se reprodujo en la nube. El contador Map es por instancia; no se presenta como límite distribuido ni se alteró para forzar un resultado.
- Registro TinyFish por referido, beneficio de créditos, prueba directa privada del paso 4 y rotación del secreto: no hay evidencia suficiente para declararlos realizados.
- Deno nativo, TalkBack, escalado de fuente y revisión visual de referencias en Android Studio: pendientes de evidencia. Las pruebas ya confirmadas de tema y rotación no sustituyen accesibilidad completa o recreación del proceso.
- Android Studio intentó anteriormente com.example.iatinyfish pese a que código, launcher y APK usan com.apps.iatinyfish. Arranque remoto por ADB verificado; sincronización del IDE y resolución definitiva del botón Run no verificadas desde el control de ventanas disponible. No se inventó una corrección de archivos coherentes.

## Publicación opcional en Google Play: procedimiento del paso 16

No ejecutado y no necesario para terminar el laboratorio:

1. Incrementar versionCode y revisar nombre, icono, permisos, textos legales y política de privacidad.
2. En Android Studio, Build → Generate Signed Bundle / APK → Android App Bundle. Crear o seleccionar upload key; guardar keystore, contraseñas y copia protegida fuera del repositorio.
3. Generar release y localizar el .aab.
4. En Play Console, crear la app, configurar Play App Signing y completar ficha, Data safety y política de privacidad.
5. Subir el AAB a Internal testing, añadir testers y validar instalación, búsqueda, apertura de fuente y errores antes de promover la versión.

Antes de distribución abierta, el paso 16 exige reemplazar la protección didáctica por autenticación/autorización, rate limiting compartido, límites de gasto y alertas. También propone observabilidad sin secretos, extracción verificable si se necesitan precios estructurados, SavedStateHandle para recreación del proceso y accesibilidad completa. Son trabajo futuro; no se implementaron ni se declaran verificados.

La variación del paso 16 que propone compilar sin -P para volver a demo no se aplica tal cual a este proyecto: gradle.properties conserva useRemoteDeals=true. Para una futura demostración local sería necesario desactivarlo explícitamente; no se cambió en este cierre.
