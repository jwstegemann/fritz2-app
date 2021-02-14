if (config.devServer) {
//   config.devServer.host = "0.0.0.0"

   config.devServer.host = "0.0.0.0"
   config.devServer.disableHostCheck = true
   config.devServer.port = 8080
   config.devServer.https = true
   config.devServer.key = "/Users/tiberius/ca/herakles.local-key.pem"
   config.devServer.cert = "/Users/tiberius/ca/herakles.local.pem"
   config.devServer.ca = "/Users/tiberius/ca/herakles.local.pem"
}