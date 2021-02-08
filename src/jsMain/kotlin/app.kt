import dev.fritz2.dom.html.render
import dev.fritz2.styling.params.styled


fun main() {

    render {
        pwa {
            brand {
                + "BRAND!"
            }

            header {
                + "HEADER!"
            }

            nav {
                (1..10).forEach {
                    (::a.styled { // nav-link
                        display { block }
                        margins { top { normal } }
                    }) {
                        +"Link $it"
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
        }
    }
}