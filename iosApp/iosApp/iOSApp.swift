import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
       var appDelegate: AppDelegate
    
    var body: some Scene {
        WindowGroup {
            ContentView(component: appDelegate.root)
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    lazy var root: RootComponent = RootComponentKt.rootComponent
}
