yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        title("$title")
    }

    body {
        h1("$title")
        h2("Armory is a WIP in progress mod. Please BackUp your worlds before using and updating the Mod!")

        div {
            headingsToCommitMap.each { k, v ->
                fragment "h2(title)", title: k
            }
            v.each {
                fragment "li(commit)", commit: it
            }
        }
    }
}