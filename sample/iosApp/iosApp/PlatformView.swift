//
//  PlatformView.swift
//  iosApp
//
//  Created by Mikita Kurhanovich on 15/09/2025.
//

import Shared
import SwiftUI

struct PlatformView: View {

    let component: PlatformComponent

    var body: some View {
        VStack(alignment: .center, spacing: 24) {
            Text(
                PlatformUiR.strings().platform_ui_hello
                    .format(args_: [component.name])
                    .localized()
            )
            Image(container: PlatformUiRiosMain.images.shared, resource: \.apple)
            //Image(uiImage: PlatformContent_iosKt.platformImageRes.toUIImage()!)
            Button(action: component.goBack) {
                Text(
                    PlatformUiR.strings().platform_ui_back_button.desc()
                        .localized()
                )
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
