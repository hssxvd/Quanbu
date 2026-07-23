const images = import.meta.glob('@/assets/img/*.{jpg,jpeg,png,gif,svg,webp}', { eager: true });

const imageMap = {};
for (const [path, module] of Object.entries(images)) {
  const filename = path.split('/').pop();
  imageMap[filename] = module.default;
}

const fallbackImage = imageMap['rice.png'] || '';

export function getImageUrl(filename) {
  if (!filename) return fallbackImage;
  if (filename.startsWith('http')) return filename;
  if (imageMap[filename]) return imageMap[filename];
  return fallbackImage;
}