import SwiftUI
import Shared

struct ContentView: View {
    @StateValue private var childStack:
           ChildStack<AnyObject, RootComponentChild>

       private let component: RootComponent
       private var activeChild: RootComponentChild { childStack.active.instance }

       init(component: RootComponent) {
           self.component = component
           _childStack = StateValue(component.childStack)
       }
           
       @ViewBuilder var body: some View {
           ChildView(child: activeChild)
               .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
       }
}

private struct ChildView: View {

    let child: RootComponentChild

    @ViewBuilder var body: some View {
        switch child {
        case let child as RootComponentChildWelcome:
            WelcomeView(component: child.component)
        case let child as RootComponentChildGuard:
            GuardView(component: child.component)
        case let child as RootComponentChildPlatform:
            PlatformView(component: child.component)
        default: fatalError("unknown child = \(child)")
        }
    }
}
