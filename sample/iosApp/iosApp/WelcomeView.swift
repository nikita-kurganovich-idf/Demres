//
//  WelcomeView.swift
//  iosApp
//
//  Created by Mikita Kurhanovich on 15/09/2025.
//

import Shared
import SwiftUI

struct WelcomeView: View {

    let component: WelcomeComponent

    var body: some View {
        VStack(alignment: .center, spacing: 24) {
            Text(WelcomeUiR.strings().welcome_ui_hello.desc().localized())
                .multilineTextAlignment(.center)

            Button(action: component.goNext) {
                Text(
                    WelcomeUiR.strings().welcome_ui_navigate_to_guard.desc()
                        .localized())
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
    }
}
