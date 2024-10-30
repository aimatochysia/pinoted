from PIL import Image

def resize_image(input_image_path):
    sizes = [(512, 512), (192, 192)]
    base_filename = input_image_path.split('.')[0]
    images_for_ico = []
    with Image.open(input_image_path) as img:
        for size in sizes:
            resized_img = img.resize(size, Image.LANCZOS)
            resized_img.save(f"{base_filename}{size[0]}.png")
            images_for_ico.append(resized_img)
        
        img.save(f"{base_filename}.ico", format='ICO', sizes=sizes)

resize_image('icon.png')
