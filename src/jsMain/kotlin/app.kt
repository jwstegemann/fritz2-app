import dev.fritz2.components.*
import dev.fritz2.dom.html.render
import dev.fritz2.styling.params.styled
import kotlinx.browser.window
import kotlinx.coroutines.flow.flowOf
import org.w3c.workers.InstallEvent
import org.w3c.workers.ServiceWorkerGlobalScope
import org.w3c.workers.ServiceWorkerRegistration
import kotlin.js.Promise

external val self: ServiceWorkerGlobalScope

const val CACHE_NAME = "my-site-cache-v1"
val urlsToCache = arrayOf(
"/",
"/st􏰁les/main.css",
"/images/dog.svg",
"/images/cat.cvg"
)
fun installServiceWorker() { self.addEventListener("install", { event ->
    event as InstallEvent
    event.waitUntil(
        self.caches.open(CACHE_NAME)
            .then { it.addAll(urlsToCache) }
    ) })
}

fun main() {
    try {
        window.addEventListener("load", {
            window.navigator.serviceWorker.register("/serviceWorker.js") })
            console.log("Service Worker registered")
    } catch (t: Throwable) {
            console.log("Error registering ervice Worker:", t)
    }

    render {
        pwa {
            brand {
                icon({
                    color { lighterGray }
                    size { "2rem" }
                    margins { right { normal } }
                }) { fromTheme { fritz2 } }
                (::span.styled{
                    fontWeight { semiBold }
                    fontSize { large }
                }) { +"Demo App" }
            }

            header {
                (::span.styled {
                    fontWeight { semiBold }
                    fontSize { large }
                }) { +"Where you are"}
            }

            actions {
                lineUp {
                    items {
                        pushButton({
                            display(sm = { none}, md = { block })
                        }) {
                            icon { fromTheme { download } }
                            text("do something")
                            color { primary }
                        }

                        pushButton {
                            icon { fromTheme { barChart } }
                            text("do nothing")
                            color { secondary }
                        }
                    }
                }
            }

            nav {
                navSection("Section Header")
                (1..5).forEach { number ->
                    val text = "Link No. $number"
                    navLink {
                        icon { fromTheme { calendar } }
                        text(text)
                        if (number == 3) active(flowOf(true))
                    } handledBy alertToast {
                        console.log("Number: $number")
                        severity { info }
                        content(text)
                    }
                }

                navSection("Another Section")
                (1..3).forEach {
                    navLink {
                        icon { fromTheme { call } }
                        text("Other Link $it")
                    }
                }
            }

            footer {
                lineUp({
                    alignItems { center }
                    justifyContent { spaceBetween }
                }) {
                    items {
                        icon({
                            size { huge }
                        }) { fromTheme { user } }
                        (::span.styled {
                            fontSize { normal }
                        }) { + "Jens Stegemann" }
                        pushButton {
                            variant { outline }
                            color { lightGray }
                            size { small }
                            icon { fromTheme { logOut } }
                        }
                    }
                }
            }

            main {
                h1 { +"Content here" }
                (1..10).forEach {
                    (::p.styled {
                        margins { top { large } }
                    }) {
                        + "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
                    }
                }
            }

            tabs {
                pushButton({
                }) {
                    icon { fromTheme { cloudUpload } }
                    variant { link }
                }
                pushButton {
                    icon { fromTheme { circleArrowLeft } }
                    variant { link }
                }
                pushButton {
                    icon { fromTheme { documentEmpty } }
                    variant { link }
                }
            }
        }
    }
}