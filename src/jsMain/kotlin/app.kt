import dev.fritz2.components.flexBox
import dev.fritz2.components.icon
import dev.fritz2.components.lineUp
import dev.fritz2.components.pushButton
import dev.fritz2.dom.html.render
import dev.fritz2.styling.params.styled


fun main() {

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
                (1..10).forEach {
                    lineUp({
                        padding { small }
                        alignItems { center }
                        if (it == 3) {
                            background { color { "rgba(0,0,0,0.2)" }}
                            borders {
                                left {
                                    width { "0.2rem" }
                                    color { lightGray }
                                }
                            }
                        }
                    }) {
                        items {
                            icon({
                                size { large }
                            }) { fromTheme { calendar } }

                            (::a.styled { // nav-link
                                display { block }

                                fontWeight { semiBold }
                                fontSize { normal }
                            }) {
                                +"Link $it"
                            }
                        }
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
                        }) { + "Jens" }
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