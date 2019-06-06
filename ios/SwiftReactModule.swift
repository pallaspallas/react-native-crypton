import Foundation

@objc(SwiftReactModule)
class SwiftReactModule: NSObject {
    
    @objc func printMessage(message: String!) {
        println("SwiftReactModule::printMessage => \(message)")
    }
    
}