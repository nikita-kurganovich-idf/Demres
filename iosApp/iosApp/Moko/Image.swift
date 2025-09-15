//
//  Image.swift
//  iosApp
//
//  Created by Mikita Kurhanovich on 15/09/2025.
//

import Shared
import SwiftUI

extension Image {
    init<T>(container: T, resource: KeyPath<T, Shared.ImageResource>) where T: ResourceContainer {
        let imageResource: Shared.ImageResource = container[keyPath: resource]
        self.init(imageResource.assetImageName, bundle: imageResource.bundle)
    }
}
