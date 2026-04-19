// 主JavaScript文件
document.addEventListener('DOMContentLoaded', function() {
    // 初始化工具提示
    initTooltips();
    
    // 初始化表单验证
    initFormValidation();
    
    // 初始化投票预览
    initPollPreview();
});

// 工具提示初始化
function initTooltips() {
    const tooltips = document.querySelectorAll('[data-tooltip]');
    tooltips.forEach(element => {
        element.addEventListener('mouseenter', showTooltip);
        element.addEventListener('mouseleave', hideTooltip);
    });
}

function showTooltip(event) {
    const tooltipText = event.target.getAttribute('data-tooltip');
    if (!tooltipText) return;
    
    const tooltip = document.createElement('div');
    tooltip.className = 'tooltip';
    tooltip.textContent = tooltipText;
    
    document.body.appendChild(tooltip);
    
    const rect = event.target.getBoundingClientRect();
    tooltip.style.position = 'fixed';
    tooltip.style.top = (rect.top - tooltip.offsetHeight - 10) + 'px';
    tooltip.style.left = (rect.left + rect.width / 2 - tooltip.offsetWidth / 2) + 'px';
    
    event.target._tooltip = tooltip;
}

function hideTooltip(event) {
    if (event.target._tooltip) {
        event.target._tooltip.remove();
        event.target._tooltip = null;
    }
}

// 表单验证
function initFormValidation() {
    const forms = document.querySelectorAll('form[data-validate]');
    forms.forEach(form => {
        form.addEventListener('submit', validateForm);
    });
}

function validateForm(event) {
    const form = event.target;
    const inputs = form.querySelectorAll('input[required], textarea[required], select[required]');
    
    let isValid = true;
    
    inputs.forEach(input => {
        if (!input.value.trim()) {
            markInvalid(input, '此字段为必填项');
            isValid = false;
        } else {
            markValid(input);
        }
    });
    
    if (!isValid) {
        event.preventDefault();
        showToast('请填写所有必填字段', 'error');
    }
}

function markInvalid(element, message) {
    element.classList.add('invalid');
    element.classList.remove('valid');
    
    let errorElement = element.nextElementSibling;
    if (!errorElement || !errorElement.classList.contains('error-message')) {
        errorElement = document.createElement('div');
        errorElement.className = 'error-message';
        element.parentNode.insertBefore(errorElement, element.nextSibling);
    }
    errorElement.textContent = message;
}

function markValid(element) {
    element.classList.add('valid');
    element.classList.remove('invalid');
    
    const errorElement = element.nextElementSibling;
    if (errorElement && errorElement.classList.contains('error-message')) {
        errorElement.remove();
    }
}

// 投票预览
function initPollPreview() {
    const optionsTextarea = document.getElementById('options');
    if (!optionsTextarea) return;
    
    const previewContainer = document.getElementById('previewContainer');
    if (!previewContainer) return;
    
    function updatePreview() {
        const options = optionsTextarea.value.split('\n')
            .map(opt => opt.trim())
            .filter(opt => opt.length > 0);
        
        if (options.length === 0) {
            previewContainer.innerHTML = '<p class="preview-empty">暂无选项预览</p>';
            return;
        }
        
        let html = '<div class="preview-list">';
        options.forEach((option, index) => {
            html += `
                <div class="preview-item">
                    <span class="preview-number">${index + 1}</span>
                    <span class="preview-text">${option}</span>
                </div>
            `;
        });
        html += '</div>';
        
        previewContainer.innerHTML = html;
    }
    
    optionsTextarea.addEventListener('input', updatePreview);
    updatePreview();
}

// Toast通知
function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.textContent = message;
    
    document.body.appendChild(toast);
    
    // 触发动画
    setTimeout(() => {
        toast.classList.add('show');
    }, 10);
    
    // 自动消失
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 300);
    }, 3000);
}

// 确认对话框
function confirmAction(message, callback) {
    const modal = document.createElement('div');
    modal.className = 'confirm-modal';
    modal.innerHTML = `
        <div class="confirm-content">
            <p>${message}</p>
            <div class="confirm-buttons">
                <button class="btn btn-outline" onclick="this.closest('.confirm-modal').remove()">取消</button>
                <button class="btn btn-primary" onclick="this.closest('.confirm-modal').remove(); callback()">确认</button>
            </div>
        </div>
    `;
    
    document.body.appendChild(modal);
    
    // 替换回调函数
    const confirmBtn = modal.querySelector('.btn-primary');
    confirmBtn.onclick = function() {
        modal.remove();
        callback();
    };
}

// 加载动画
function showLoading(container) {
    const loading = document.createElement('div');
    loading.className = 'loading';
    loading.innerHTML = '<div class="spinner"></div>';
    
    if (typeof container === 'string') {
        container = document.querySelector(container);
    }
    
    if (container) {
        container.appendChild(loading);
    } else {
        document.body.appendChild(loading);
    }
    
    return loading;
}

function hideLoading(loadingElement) {
    if (loadingElement) {
        loadingElement.remove();
    }
}

// 复制到剪贴板
function copyToClipboard(text) {
    navigator.clipboard.writeText(text)
        .then(() => {
            showToast('已复制到剪贴板', 'success');
        })
        .catch(err => {
            console.error('复制失败:', err);
            showToast('复制失败', 'error');
        });
}

// 防抖函数
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// 节流函数
function throttle(func, limit) {
    let inThrottle;
    return function() {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
            func.apply(context, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// 添加额外的CSS样式
const additionalStyles = `
    .tooltip {
        position: absolute;
        background: var(--dark-color);
        color: white;
        padding: 0.5rem 0.75rem;
        border-radius: var(--radius);
        font-size: 0.875rem;
        z-index: 10000;
        pointer-events: none;
        white-space: nowrap;
        box-shadow: var(--shadow-lg);
    }
    
    .tooltip::after {
        content: '';
        position: absolute;
        top: 100%;
        left: 50%;
        transform: translateX(-50%);
        border-width: 5px;
        border-style: solid;
        border-color: var(--dark-color) transparent transparent transparent;
    }
    
    .invalid {
        border-color: var(--danger-color) !important;
    }
    
    .valid {
        border-color: var(--secondary-color) !important;
    }
    
    .error-message {
        color: var(--danger-color);
        font-size: 0.875rem;
        margin-top: 0.25rem;
    }
    
    .toast {
        position: fixed;
        bottom: 2rem;
        right: 2rem;
        padding: 1rem 1.5rem;
        border-radius: var(--radius);
        color: white;
        transform: translateY(100px);
        opacity: 0;
        transition: all 0.3s ease;
        z-index: 10000;
        box-shadow: var(--shadow-lg);
    }
    
    .toast.show {
        transform: translateY(0);
        opacity: 1;
    }
    
    .toast-success {
        background: var(--secondary-color);
    }
    
    .toast-error {
        background: var(--danger-color);
    }
    
    .toast-info {
        background: var(--info-color);
    }
    
    .confirm-modal {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 10000;
    }
    
    .confirm-content {
        background: white;
        padding: 2rem;
        border-radius: var(--radius-lg);
        max-width: 400px;
        width: 90%;
        box-shadow: var(--shadow-lg);
    }
    
    .confirm-buttons {
        display: flex;
        gap: 1rem;
        margin-top: 1.5rem;
        justify-content: flex-end;
    }
    
    .loading {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(255, 255, 255, 0.8);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 1000;
    }
    
    .spinner {
        width: 40px;
        height: 40px;
        border: 3px solid var(--border-color);
        border-top-color: var(--primary-color);
        border-radius: 50%;
        animation: spin 1s linear infinite;
    }
    
    @keyframes spin {
        to { transform: rotate(360deg); }
    }
    
    .preview-list {
        margin-top: 1rem;
    }
    
    .preview-item {
        display: flex;
        align-items: center;
        padding: 0.5rem;
        background: var(--light-color);
        border-radius: var(--radius);
        margin-bottom: 0.5rem;
    }
    
    .preview-number {
        background: var(--primary-color);
        color: white;
        width: 24px;
        height: 24px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 0.75rem;
        margin-right: 0.75rem;
    }
    
    .preview-text {
        flex: 1;
    }
    
    .preview-empty {
        color: var(--gray-color);
        font-style: italic;
        text-align: center;
        padding: 1rem;
    }
`;

// 添加样式到页面
const styleElement = document.createElement('style');
styleElement.textContent = additionalStyles;
document.head.appendChild(styleElement);