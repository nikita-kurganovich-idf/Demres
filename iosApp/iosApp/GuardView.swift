//
//  GuardView.swift
//  iosApp
//
//  Created by Mikita Kurhanovich on 15/09/2025.
//

import Shared
import SwiftUI

struct GuardView: View {

    private let component: GuardComponent
    @StateValue private var model: GuardComponentModel

    init(component: GuardComponent) {
        self.component = component
        self._model = StateValue(component.model)
    }

    var body: some View {
        VStack(alignment: .center, spacing: 24) {
            Button(GuardUiR.strings().guard_ui_show_image_button.desc().localized()) {
                withAnimation {
                    component.showImage()
                }
            }
            if model.isImageShown {
                Image(container: GuardUiR.images(), resource: \.chimera)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 300, height: 300)
            }

            Button(action: component.goToPlatform) {
                Text(GuardUiR.strings().guard_ui_next_button.desc().localized())
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
    }
}
